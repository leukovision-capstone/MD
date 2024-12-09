import org.jetbrains.kotlin.gradle.targets.js.KotlinJsCompilerAttribute

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.capstone.leukovision"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.capstone.leukovision"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Menambahkan API URL dari properti proyek
        val apiUrl: String? = project.findProperty("LeukoVision_API") as String?
        buildConfigField("String", "LEUKOVISION_API", "\"${apiUrl ?: "https://default-api-url.com"}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Menambahkan API URL untuk build release
            buildConfigField("String", "LEUKOVISION_API", "\"https://release-api-url.com\"")
        }
        getByName("debug") {
            // Menambahkan API URL untuk build debug
            buildConfigField("String", "LEUKOVISION_API", "\"https://debug-api-url.com\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        mlModelBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.play.services.basement)
    implementation(libs.androidx.gridlayout)
    implementation(libs.kotlin.reflect)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.mediation.test.suite)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.legacy.support.v13)

    // Dependensi terbaru ditambahkan di bawah ini
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.task.vision)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.tensorflow.lite) // Versi terbaru

    implementation(libs.ucrop)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.squareup.picasso)

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}