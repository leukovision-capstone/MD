// build.gradle.kts (Module :app)
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Tetap menggunakan KAPT
    id("androidx.navigation.safeargs.kotlin") // Pastikan plugin Safe Args ada jika Anda menggunakannya
}

android {
    namespace = "com.capstone.leukovision"
    compileSdk = 36 // Ini harus langsung di bawah 'android {'

    defaultConfig {
        applicationId = "com.capstone.leukovision"
        minSdk = 26     // Ini harus di dalam 'defaultConfig {'
        targetSdk = 36  // Ini harus di dalam 'defaultConfig {'
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true // Aktifkan View Binding
    }
}

dependencies {

    // Gunakan bundles untuk dependensi Android inti dan networking.
    // Ini sudah mencakup semua library yang Anda deklarasikan secara individual sebelumnya.
    implementation(libs.bundles.android.essential)
    implementation(libs.bundles.networking)

    // Ini adalah dependensi khusus yang membutuhkan 'kapt' untuk annotation processing.
    kapt(libs.glide.compiler)

    // Dependensi Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}