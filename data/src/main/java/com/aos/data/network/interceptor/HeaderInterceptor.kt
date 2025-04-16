package com.aos.data.network.interceptor

import com.aos.core.util.TokenUtil
import com.aos.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl: HttpUrl = originalRequest.url

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${TokenUtil.getAccessToken()}")
            .build()
        return chain.proceed(newRequest)
    }
}
