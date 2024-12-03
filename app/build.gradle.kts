plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

android {
    namespace = "com.capstone.leukovision"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.capstone.leukovision"
        minSdk = 21
        targetSdk = 35
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

    packaging {
        resources.excludes.add("META-INF/INDEX.LIST")
        // Jika ada file .so tertentu yang ingin dikecualikan
        jniLibs.excludes.add("**/*.so")
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
            buildConfig = true
            mlModelBinding = true
        }
    }

    dependencies {
        // AndroidX Libraries
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)  // Hanya satu yang digunakan
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.androidx.preference.ktx)
        implementation(libs.androidx.preference)
        implementation(libs.mediation.test.suite)
        implementation(libs.androidx.datastore.preferences)  // Disarankan menggunakan preferences atau core, bukan keduanya
        implementation(libs.androidx.databinding.runtime)
        implementation(libs.androidx.room.runtime)  // Seharusnya room-runtime
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        // Lifecycle and ViewModel
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Kotlin Coroutines
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)

        // RecyclerView
        implementation(libs.androidx.recyclerview)

        // TensorFlow
        implementation(libs.tensorflow.lite)
        implementation(libs.tensorflow.lite.support)
        implementation(libs.tensorflow.lite.task.vision)
        implementation(libs.tensorflow.lite.metadata) // Hanya pilih satu versi metadata yang sesuai

        // Firebase and Google Cloud Storage
        implementation(libs.firebase.storage)
        implementation(libs.firebase.storage.ktx)
        implementation(libs.google.cloud.storage)
        implementation(libs.logging.interceptor)


        implementation(libs.play.services.basement)
        implementation(libs.squareup.picasso)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.glide)

        ksp(libs.androidx.room.compiler)
        implementation(libs.androidx.room.ktx)
        implementation(libs.androidx.work.runtime)
        implementation(libs.androidx.legacy.support.v4)
        implementation(libs.androidx.fragment.ktx)
        implementation(libs.androidx.legacy.support.v13)

        implementation("com.github.yalantis:ucrop:2.2.8")
        implementation ("androidx.activity:activity-ktx:1.7.0") // atau versi terbaru
        implementation ("androidx.activity:activity:1.7.0")
        implementation ("androidx.compose.material3:material3:1.2.0")
        implementation ("androidx.navigation:navigation-compose:2.7.6")
    }