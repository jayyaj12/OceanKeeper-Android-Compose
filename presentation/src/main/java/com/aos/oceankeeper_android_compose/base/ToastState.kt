package com.aos.oceankeeper_android_compose.base

data class ToastState(
    val text: String = "",
    val isVisible: Boolean = false,
    val toastType: ToastType = ToastType.DEFAULT
)
