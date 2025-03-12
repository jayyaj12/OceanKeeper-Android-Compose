package com.aos.oceankeeper_android_compose.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aos.oceankeeper_android_compose.ui.Screen
import com.letspl.oceankeeper.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(2000)
        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(Screen.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(isVisible, enter = fadeIn(animationSpec = tween(2000))) {
            Image(
                modifier = Modifier.size(235.dp, 43.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "앱 로고"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SplashPreview() {
//    SplashScreen(nav)
}