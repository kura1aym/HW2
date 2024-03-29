plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "dev.androidbroadcast.newssearchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.androidbroadcast.newssearchapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "NEWS_API_KEY", "\"929bd6d7ba07471db634d2a1f3f90023\"")
        buildConfigField("String", "NEWS_API_BASE_URL", "\"https://newsapi.org/v2\"")
    }

    buildTypes {
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.okhttp)

    implementation(project(":newsapi"))
    implementation(project(":news-data"))
    implementation(project(":database"))
    implementation(project(":news-common"))
    implementation(project(":features:news-main"))
    implementation(project(":news-uikit"))
    debugImplementation(libs.logging.interceptor)
}