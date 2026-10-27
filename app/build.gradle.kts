plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)

    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
}

android {
    namespace = "com.example.myfin"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.myfin"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Расширенная библиотека иконок от compose
    implementation("androidx.compose.material:material-icons-extended")

    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.foundation)

    // Liquid glass
    implementation("io.github.kyant0:backdrop:2.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
