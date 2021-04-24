package com.pbsarathy21.trendingrepos.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbsarathy21.trendingrepos.R
import com.pbsarathy21.trendingrepos.databinding.ActivityTrendingRepoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingRepoActivity : AppCompatActivity() {

    private val viewModel by viewModels<TrendingRepoViewModel>()
    private lateinit var binding: ActivityTrendingRepoBinding

    private val itemDecoration by lazy {
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL).also {
            ContextCompat.getDrawable(this, R.drawable.divider)?.let { it1 ->
                it.setDrawable(it1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingRepoBinding.inflate(layoutInflater)
        binding.repoList.hasFixedSize()
        binding.repoList.layoutManager = LinearLayoutManager(this)
        binding.repoList.addItemDecoration(itemDecoration)
        setContentView(binding.root)
    }
}