import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.letspl.oceankeeper" // ✅ 패키지명 변경
//    namespace = "com.example.oceankeeper_android_compose"
    compileSdk = 35

    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    defaultConfig {
//        applicationId = "com.example.oceankeeper_android_compose"
        applicationId = "com.letspl.oceankeeper" // ✅ 변경

        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "KAKAO_APP_KEY", "${properties["kakao.app.key"]}")
        resValue("string", "kakao_app_key", "${properties["kakao.app.key"]}")

        buildConfigField("String", "NAVER_BASE_URL", "${properties["naver.base.url"]}")
        buildConfigField("String", "NAVER_SECRET_CLIENT_ID", "${properties["naver.serect.client.id"]}")
        buildConfigField("String", "NAVER_CLIENT_ID", "${properties["naver.client.id"]}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "${properties["production.base.url"]}")
        }
        debug {
            buildConfigField("String", "BASE_URL", "${properties["develop.base.url"]}")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    // 페이징 추가
    implementation("androidx.paging:paging-compose:3.3.2")
    // 페이지 인디케이터 추가
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1")

    // coil 이미지 라이브러리 
    implementation("io.coil-kt:coil-compose:2.6.0")
    // 네이버 로그인
//    implementation("com.navercorp.nid:oauth:5.10.0") // jdk 11
    implementation("com.navercorp.nid:oauth-jdk8:5.10.0") // jdk 8
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("com.google.firebase:firebase-analytics:21.5.1")

    // 카카오로그인
    implementation("com.kakao.sdk:v2-all:2.20.6") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.20.6") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-share:2.20.6") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-talk:2.20.6") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-friend:2.20.6") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-navi:2.20.6") // 카카오내비 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.20.6") // 카카오톡 인증 서비스 API 모듈

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}