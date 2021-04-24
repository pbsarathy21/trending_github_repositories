package com.pbsarathy21.trendingrepos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbsarathy21.trendingrepos.data.models.Repository
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        triggerEvent(ErrorEvent(throwable.message))
        triggerEvent(StopLoading)
        Timber.e(throwable)
    }

    private val _eventHandler = Channel<EventHandler>()
    val eventHandler = _eventHandler.receiveAsFlow()

    init {
        getTrendingRepositories()
    }

    sealed class EventHandler {
        data class UpdateRepositories(val repositories: List<Repository>) : EventHandler()
        data class ErrorEvent(val message: String?) : EventHandler()
        object StartLoading : EventHandler()
        object StopLoading : EventHandler()
    }

    private fun triggerEvent(event: EventHandler) {
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) { _eventHandler.send(event) }
    }

    private fun getTrendingRepositories() =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            triggerEvent(StartLoading)
            repo.getTrendingRepositories().apply { triggerEvent(UpdateRepositories(this)) }
            triggerEvent(StopLoading)
        }
}