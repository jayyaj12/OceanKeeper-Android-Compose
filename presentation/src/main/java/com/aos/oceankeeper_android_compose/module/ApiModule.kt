package com.aos.oceankeeper_android_compose.module

import com.aos.data.BuildConfig
import com.aos.data.network.adapter.CustomCallAdapterFactory
import com.aos.data.network.interceptor.HeaderInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor, loggingInterceptor: HttpLoggingInterceptor,
    ) = run {
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideOceanRetrofit(okHttpClient: OkHttpClient): Retrofit = run {
        Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CustomCallAdapterFactory()).addConverterFactory(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true // 지정되지 않은 key 값은 무시
                    coerceInputValues = true // default 값 설정
                    explicitNulls = false // 없는 필드는 null로 설정
                }.asConverterFactory("application/json".toMediaType())
            ).build()
    }
}