package com.aos.oceankeeper_android_compose.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

object ToastHandler {
    private val _toastState = MutableStateFlow(ToastState()) // 초기값을 false로 설정
    val toastState: StateFlow<ToastState> = _toastState.asStateFlow()

    fun show(text: String, toastType: ToastType) {
        _toastState.value = ToastState(text = text, isVisible = true, toastType = toastType)
    }

    fun hide() {
        _toastState.value = ToastState(isVisible = false)
    }
}

