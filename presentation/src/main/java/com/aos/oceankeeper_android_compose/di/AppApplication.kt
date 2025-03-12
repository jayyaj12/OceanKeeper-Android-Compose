package com.aos.oceankeeper_android_compose.di

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.letspl.oceankeeper.BuildConfig
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
        FirebaseApp.initializeApp(this)
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_SECRET_CLIENT_ID, "오션키퍼")
    }

}