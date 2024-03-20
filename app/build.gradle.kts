plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.benyq.sodaworld"
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "com.benyq.sodaworld"
        minSdk = rootProject.ext["minSdkVersion"] as Int
        targetSdk = rootProject.ext["targetSdkVersion"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "x86", "arm64-v8a", "x86_64"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("profile") {
            initWith(getByName("debug"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding{
        enable = true
    }
}

dependencies {
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation(project(":common:base"))
    implementation(project(":common:database"))

    implementation(project(":modules:music"))
    implementation(project(":modules:account"))
    implementation(project(":modules:wanandroid"))
    debugImplementation("com.benyq.flutter_wanandroid:flutter_debug:1.0")
    add("profileImplementation", "com.benyq.flutter_wanandroid:flutter_profile:1.0")
    releaseImplementation("com.benyq.flutter_wanandroid:flutter_release:1.0")
}