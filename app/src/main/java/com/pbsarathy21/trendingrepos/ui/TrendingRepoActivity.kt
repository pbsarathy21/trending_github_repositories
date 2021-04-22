package com.pbsarathy21.trendingrepos.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pbsarathy21.trendingrepos.databinding.ActivityTrendingRepoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingRepoActivity : AppCompatActivity() {

    private val viewModel by viewModels<TrendingRepoViewModel>()
    private lateinit var binding: ActivityTrendingRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}