import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.3" apply true
}

allprojects.onEach { project ->
//    project.plugins.apply(libs.plugins.detekt.get().pluginId)
    project.afterEvaluate {
        with(project.plugins) {
            if (hasPlugin(libs.plugins.jetbrainsKotlinAndroid.get().pluginId)
                || hasPlugin(libs.plugins.jetbrainsKotlinJvm.get().pluginId)
            ) {
                apply(libs.plugins.detekt.get().pluginId)
                project.extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("default-detekt-config.yml"))
                }

                project.dependencies.add("detektPlugins", libs.detekt.formmatting.get().toString())
            }
        }
    }
}
