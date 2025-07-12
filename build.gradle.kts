// build.gradle.kts (Project :your-project-name)
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // TAMBAHKAN BARIS INI UNTUK PLUGIN SAFE ARGS
    id("androidx.navigation.safeargs") version "2.7.7" apply false // Pastikan versinya cocok dengan navigation library Anda
}

// Anda bisa memiliki bagian lain di sini, seperti allprojects, subprojects, atau tasks
// Tidak perlu mengubah jika Anda tidak punya konfigurasi tambahan di bagian ini