package com.aos.oceankeeper_android_compose

import com.letspl.oceankeeper.R

sealed class BottomNavScreen(val route: String, val label: String, val selectIcon: Int, val unSelectedIcon: Int) {
    object Home : BottomNavScreen("home_screen", "홈", R.drawable.icon_nav_home_selected, R.drawable.icon_nav_home_unselected)
    object Message : BottomNavScreen("message_screen", "쪽지함", R.drawable.icon_nav_empty_message_selected, R.drawable.icon_nav_empty_message_unselected)
    object MyActivity : BottomNavScreen("my_activity_screen", "나의 활동", R.drawable.icon_nav_myactivity_selected, R.drawable.icon_nav_myactivity_unselected)

    companion object {
        fun items(): List<BottomNavScreen> = listOf(Home, Message, MyActivity)
    }
}