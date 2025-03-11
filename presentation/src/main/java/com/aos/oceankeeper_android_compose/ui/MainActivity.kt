package com.aos.oceankeeper_android_compose.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aos.oceankeeper_android_compose.base.GlobalLoadingScreen
import com.aos.oceankeeper_android_compose.base.GlobalToastScreen
import com.aos.oceankeeper_android_compose.screen.login.LoginScreen
import com.aos.oceankeeper_android_compose.screen.splash.SplashScreen
import com.aos.oceankeeper_android_compose.ui.theme.OceanKeeperAndroidComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
                        }

                        GlobalLoadingScreen()
                        GlobalToastScreen()
                    }
                }
            }
        }
    }
}