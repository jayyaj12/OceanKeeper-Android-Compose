package com.aos.core.util

object UserInfoUtil {
    private val userInfo: UserInfo = UserInfo()

    fun setUserInfo(deviceToken: String?, email: String?, nickname: String?, provider: String?, providerId: String?, profileImgUrl: String?) {
        userInfo.deviceToken = deviceToken ?: ""
        userInfo.email = email ?: ""
        userInfo.nickname = nickname ?: ""
        userInfo.provider = provider ?: ""
        userInfo.providerId = providerId ?: ""
        userInfo.profileImgUrl = profileImgUrl ?: ""
    }

    fun setUserProfile(profileImgUrl: String) {
        userInfo.profileImgUrl = profileImgUrl
    }

    fun setUserId(userId: String) {
        userInfo.userId = userId
    }

    fun getUserInfo(): UserInfo {
        return userInfo
    }

    data class UserInfo(
        var userId: String ="",
        var deviceToken: String ="",
        var email: String ="",
        var nickname: String = "",
        var provider: String = "",
        var providerId: String = "",
        var profileImgUrl: String = ""

    )
}