package com.aos.core.util

object TokenUtil {

    private var accessToken = ""
    private var refreshToken = ""

    fun setToken(accessToken: String, refreshToken: String){
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    fun getAccessToken(): String {
        return accessToken
    }

}