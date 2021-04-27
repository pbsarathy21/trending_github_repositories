package com.pbsarathy21.trendingrepos.ui

import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbsarathy21.trendingrepos.R
import com.pbsarathy21.trendingrepos.databinding.ActivityTrendingRepoBinding
import com.pbsarathy21.trendingrepos.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TrendingRepoActivity : AppCompatActivity() {

    private val viewModel by viewModels<TrendingRepoViewModel>()
    private lateinit var binding: ActivityTrendingRepoBinding

    private val repositoryAdapter by lazy { RepositoryAdapter() }

    private val itemDecoration by lazy {
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL).also {
            ContextCompat.getDrawable(this, R.drawable.divider)?.let { it1 ->
                it.setDrawable(it1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M -> {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        binding = ActivityTrendingRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.repoList.hasFixedSize()
        binding.repoList.layoutManager = LinearLayoutManager(this)
        binding.repoList.adapter = repositoryAdapter
        binding.repoList.addItemDecoration(itemDecoration)

        lifecycleScope.launchWhenResumed {
            viewModel.eventHandler.collect {
                when (it) {
                    is TrendingRepoViewModel.EventHandler.ErrorEvent -> toast(it.message)
                    is TrendingRepoViewModel.EventHandler.StartLoading -> binding.progress.isVisible =
                        true
                    is TrendingRepoViewModel.EventHandler.StopLoading -> binding.progress.isVisible =
                        false
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.repositories.collect {
                repositoryAdapter.updateRepositories(it)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getTrendingRepositories()
        }
    }
}