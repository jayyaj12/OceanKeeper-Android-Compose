package com.aos.core.util

object UserInfoUtil {
    private val userInfo: UserInfo = UserInfo()

    fun setUserInfo(nickname: String?, profileImgUrl: String?) {
        userInfo.nickname = nickname ?: ""
        userInfo.profileImgUrl = profileImgUrl ?: ""
    }

    fun getUserInfo(): UserInfo {
        return userInfo
    }

    data class UserInfo(
        var nickname: String = "",
        var profileImgUrl: String = ""
    )
}