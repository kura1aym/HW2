// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //alias(libs.plugins.android.application) apply false
    id("com.android.application") version "8.1.1" apply false
//    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
//    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
//    alias(libs.plugins.kotlin.serialization) apply false

    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
    //alias(libs.plugins.com.android.library) apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
}