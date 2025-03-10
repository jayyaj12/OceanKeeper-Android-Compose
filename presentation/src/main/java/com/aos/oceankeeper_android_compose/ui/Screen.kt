package com.aos.oceankeeper_android_compose.ui

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object SignUpInputScreen: Screen("sign_up_input_screen")
    data object SignUpCompleteScreen: Screen("sign_up_complete_screen")
}