package com.pbsarathy21.trendingrepos.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbsarathy21.trendingrepos.data.repos.TrendingRepository
import com.pbsarathy21.trendingrepos.ui.TrendingRepoViewModel.EventHandler.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrendingRepoViewModel @Inject constructor(private val repo: TrendingRepository) :
    ViewModel() {

    // ui attributes

    val isSearchEnabled = ObservableField(false)
    val isSearchIconVisible = ObservableField(true)
    val searchQuery = ObservableField<String>()
    val errorState = ObservableField(false)
    val errorText = ObservableField<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        triggerEvent(StopLoading)
        errorState.set(true)
        isSearchIconVisible.set(false)
        errorText.set(throwable.message)
        Timber.e(throwable)
    }

    val repositories = repo.repositories

    private val _eventHandler = Channel<EventHandler>()
    val eventHandler = _eventHandler.receiveAsFlow()

    sealed class EventHandler {
        object StartLoading : EventHandler()
        object StopLoading : EventHandler()
    }

    private fun triggerEvent(event: EventHandler) {
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) { _eventHandler.send(event) }
    }

    fun getTrendingRepositories() =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            errorState.set(false)
            triggerEvent(StartLoading)
            repo.getTrendingRepositories()
            triggerEvent(StopLoading)
        }

    fun filterRepositories(filterText: String) = repo.filterRepositories(filterText)
}