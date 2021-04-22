package com.pbsarathy21.trendingrepos.ui

import androidx.lifecycle.ViewModel
import com.pbsarathy21.trendingrepos.data.repos.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingRepoViewModel @Inject constructor(private val repo: TrendingRepository) : ViewModel() {
}