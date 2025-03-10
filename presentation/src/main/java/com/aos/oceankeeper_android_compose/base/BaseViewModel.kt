package com.aos.oceankeeper_android_compose.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _baseStateFlow = MutableStateFlow<Event>(Event.Nothing)
    val baseStateFlow = _baseStateFlow.asStateFlow()
    fun baseEvent(event: Event) {
        viewModelScope.launch {
            _baseStateFlow.emit(event)
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