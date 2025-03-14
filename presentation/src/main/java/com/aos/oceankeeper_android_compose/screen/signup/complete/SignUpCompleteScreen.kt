package com.aos.oceankeeper_android_compose.screen.signup.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun SignUpCompleteScreen(navController: NavController) {
    Box(modifier = Modifier.background(Color.White)) {
        SignUpCompleteUi(navController)
    }
}

@Composable
fun SignUpCompleteUi(navController: NavController) {

}