package com.aos.oceankeeper_android_compose.screen.login

data class LoginState(
    val text: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
