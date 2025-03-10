package com.aos.oceankeeper_android_compose.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    private val _baseSharedFlow = MutableSharedFlow<Event>(replay = 0, extraBufferCapacity = 1)
    val baseSharedFlow = _baseSharedFlow.asSharedFlow()
    fun baseEvent(event: Event) {
        viewModelScope.launch {
            Timber.e("baseEvent")
            withContext(Dispatchers.IO) {
                _baseSharedFlow.emit(event)
            }
        }
    }
    sealed class Event {
        data class ShowToast(val message: String) : Event()
        data class ShowToastRes(@StringRes val message: Int) : Event()
        data class ShowErrorToast(val message: String) : Event()
//        data class ShowSuccessToast(val message: String) : Event()
//        data class ShowSuccessToastRes(@StringRes val message: Int) : Event()

        object Nothing: Event()
        object ShowLoading: Event()
        object HideLoading: Event()
        object ExpiredToken: Event()
    }
}