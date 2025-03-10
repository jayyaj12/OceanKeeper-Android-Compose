import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
    namespace = "com.aos.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "NAVER_BASE_URL", "${properties["movie.api.key"]}")
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "${properties["productuin.base.url"]}")
        }

        debug {
            buildConfigField("String", "BASE_URL", "${properties["develop.base.url"]}")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))

    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.timber)
    implementation(libs.hilt.android)
    implementation(libs.retrofit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}