package com.aos.oceankeeper_android_compose.module

import com.aos.data.network.api.OceanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideOceanService(retrofit: Retrofit): OceanService = retrofit.create(OceanService::class.java)

//    @Provides
//    @Singleton
//    fun provideNaverService(okHttpClient: OkHttpClient):  {
//        val retrofit = Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("https://openapi.naver.com/v1/util/")
//            .addCallAdapterFactory(CustomCallAdapterFactory())
//            .addConverterFactory(
//                Json {
//                    isLenient = true
//                    ignoreUnknownKeys = true // 지정되지 않은 key 값은 무시
//                    coerceInputValues = true // default 값 설정
//                    explicitNulls = false // 없는 필드는 null로 설정
//                }.asConverterFactory("application/json".toMediaType())
//            )
//            .build()
//        return retrofit.create(NaverShortenUrlService::class.java)
//    }
}

