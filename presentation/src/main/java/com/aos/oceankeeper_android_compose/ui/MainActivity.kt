package com.aos.oceankeeper_android_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aos.oceankeeper_android_compose.base.GlobalLoadingScreen
import com.aos.oceankeeper_android_compose.base.GlobalToastScreen
import com.aos.oceankeeper_android_compose.screen.login.LoginScreen
import com.aos.oceankeeper_android_compose.screen.signup.complete.SignUpCompleteScreen
import com.aos.oceankeeper_android_compose.screen.signup.input.SingUpInputScreen
import com.aos.oceankeeper_android_compose.screen.splash.SplashScreen
import com.aos.oceankeeper_android_compose.ui.theme.OceanKeeperAndroidComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OceanKeeperAndroidComposeTheme {
                Surface(color = Color.White) {
                    Box {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen.route
                        ) {
                            composable(
                                route = Screen.SplashScreen.route
                            ) {
                                SplashScreen(navController)
                            }
                            composable(
                                route = Screen.LoginScreen.route
                            ) {
                                LoginScreen(navController)
                            }
                            composable(
                                route = Screen.SignUpInputScreen.route
                            ) {
                                SingUpInputScreen(navController)
                            }
                            composable(
                                route = Screen.SignUpCompleteScreen.route
                            ) {
                                SignUpCompleteScreen(navController)
                            }
                            composable(
                                route = Screen.HomeScreen.route
                            ) {
                                SignUpCompleteScreen(navController)
                            }
                        }

                        GlobalLoadingScreen()
                        GlobalToastScreen()
                    }
                }
            }
        }
    }
}
