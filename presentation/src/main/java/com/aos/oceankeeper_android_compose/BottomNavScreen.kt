package com.aos.oceankeeper_android_compose

import com.letspl.oceankeeper.R

sealed class BottomNavScreen(val route: String, val label: String, val icon: Int) {
    object Home : BottomNavScreen("home_screen", "홈", R.drawable.bottom_nav_home_icon_pressed)
    object Message : BottomNavScreen("message_screen", "쪽지함", R.drawable.bottom_nav_msg_icon_pressed)
    object MyActivity : BottomNavScreen("my_activity_screen", "나의 활동", R.drawable.bottom_nav_my_activity_pressed)

    companion object {
        fun items(): List<BottomNavScreen> = listOf(Home, Message, MyActivity)
    }
}