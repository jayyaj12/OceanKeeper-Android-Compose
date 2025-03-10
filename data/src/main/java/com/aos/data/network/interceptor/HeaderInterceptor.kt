package com.aos.data.network.interceptor

import com.aos.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl: HttpUrl = originalRequest.url()

        val newUrl = originalUrl.newBuilder()
//            .addQueryParameter("key", BuildConfig.MOVIE_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
