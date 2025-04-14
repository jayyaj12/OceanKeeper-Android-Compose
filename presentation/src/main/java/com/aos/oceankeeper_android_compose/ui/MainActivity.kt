package com.aos.oceankeeper_android_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aos.oceankeeper_android_compose.BottomNavScreen
import com.aos.oceankeeper_android_compose.base.GlobalLoadingScreen
import com.aos.oceankeeper_android_compose.base.GlobalToastScreen
import com.aos.oceankeeper_android_compose.screen.home.HomeScreen
import com.aos.oceankeeper_android_compose.screen.login.LoginScreen
import com.aos.oceankeeper_android_compose.screen.signup.complete.SignUpCompleteScreen
import com.aos.oceankeeper_android_compose.screen.signup.input.SingUpInputScreen
import com.aos.oceankeeper_android_compose.screen.splash.SplashScreen
import com.aos.oceankeeper_android_compose.ui.theme.OceanKeeperAndroidComposeTheme
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.letspl.oceankeeper.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OceanKeeperAndroidComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in listOf(
                    BottomNavScreen.Home.route,
                    BottomNavScreen.Message.route,
                    BottomNavScreen.MyActivity.route,
                )

                Timber.e("showBottomBar $showBottomBar")
                Scaffold(bottomBar = {
                    if (showBottomBar) {
                        BottomNavBar(navController)
                    }
                }) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen.route
                        ) {
                            // 로그인 플로우
                            composable(Screen.SplashScreen.route) { SplashScreen(navController) }
                            composable(Screen.LoginScreen.route) { LoginScreen(navController) }
                            composable(Screen.SignUpInputScreen.route) {
                                SingUpInputScreen(
                                    navController
                                )
                            }
                            composable(Screen.SignUpCompleteScreen.route) {
                                SignUpCompleteScreen(
                                    navController
                                )
                            }

                            composable(
                                route = Screen.HomeScreen.route
                            ) {
                                HomeScreen(navController)
                            }
                            // 바텀 네비게이션 영역
                            composable(BottomNavScreen.Home.route) { HomeScreen(navController) }
                            composable(BottomNavScreen.Message.route) { /* MyPageScreen(navController) */ }
                            composable(BottomNavScreen.MyActivity.route) { /* SettingsScreen(navController) */ }
                        }

                        GlobalLoadingScreen()
                        GlobalToastScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        Timber.e("BottomNavScreen.items ${BottomNavScreen.items()}")
        BottomNavScreen.items().forEach { screen ->
            Timber.e("currentRoute $currentRoute")
            NavigationBarItem(icon = {
                Icon(
                    painterResource(screen.icon),
                    tint = Color.Unspecified,
                    contentDescription = screen.label,
                    modifier = Modifier.size(
                        when (screen.route) {
                            "home_screen" -> {
                                24.dp
                            }

                            "message_screen" -> {
                                25.dp
                            }

                            else -> {
                                20.dp
                            }
                        }
                    )

                )
            }, label = {
                Text(
                    text = screen.label,
                    fontSize = 12.sp,
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(
                        id = if(currentRoute == "home_screen") {
                            R.color.gray_600
                        } else {
                            R.color.gray_600
                        }
                    )
                )
            }, selected = currentRoute == screen.route, onClick = {
                if (currentRoute != screen.route) {
                    navController.navigate(screen.route) {
                        popUpTo(BottomNavScreen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            })
        }
    }
}
