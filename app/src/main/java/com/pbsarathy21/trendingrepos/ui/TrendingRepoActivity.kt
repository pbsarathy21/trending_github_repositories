package com.pbsarathy21.trendingrepos.ui

import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbsarathy21.trendingrepos.R
import com.pbsarathy21.trendingrepos.data.models.Repository
import com.pbsarathy21.trendingrepos.databinding.ActivityTrendingRepoBinding
import com.pbsarathy21.trendingrepos.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

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
        setStatusBarDecors()
        binding = ActivityTrendingRepoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        binding.repoList.hasFixedSize()
        binding.repoList.layoutManager = LinearLayoutManager(this)
        binding.repoList.adapter = repositoryAdapter
        binding.repoList.addItemDecoration(itemDecoration)

        binding.appHeader.setOnClickListener { enableSearchView() }
        binding.searchIcon.setOnClickListener { enableSearchView() }
        binding.backArrowIcon.setOnClickListener { disableSearchView() }
        binding.closeIcon.setOnClickListener { clearSearchQuery() }
        binding.tryAgainButton.setOnClickListener { viewModel.getTrendingRepositories() }

        lifecycleScope.launchWhenResumed {
            viewModel.eventHandler.collect {
                when (it) {
                    is TrendingRepoViewModel.EventHandler.StartLoading -> binding.progress.isVisible =
                        true
                    is TrendingRepoViewModel.EventHandler.StopLoading -> binding.progress.isVisible =
                        false
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.repositories.collect {
                if (it.isEmpty())
                    viewModel.getTrendingRepositories()
                viewModel.isSearchIconVisible.set(it.isNotEmpty())
                repositoryAdapter.updateRepositories(it)
            }
        }

        binding.searchQueryEditText.doAfterTextChanged {
            lifecycleScope.launchWhenResumed {
                val repositories: List<Repository>
                withContext(Dispatchers.IO) {
                    repositories = viewModel.filterRepositories(it?.toString() ?: "")
                }
                repositoryAdapter.updateRepositories(repositories)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getTrendingRepositories()
        }
    }

    private fun setStatusBarDecors() {
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
    }

    private fun enableSearchView() {
        viewModel.isSearchEnabled.set(true)
        viewModel.isSearchIconVisible.set(false)
        binding.searchQueryEditText.requestFocus()
    }

    private fun disableSearchView() {
        viewModel.isSearchEnabled.set(false)
        viewModel.isSearchIconVisible.set(true)
        binding.searchQueryEditText.clearFocus()
        hideKeyboard()
    }

    private fun clearSearchQuery() {
        viewModel.searchQuery.set("")
    }
}