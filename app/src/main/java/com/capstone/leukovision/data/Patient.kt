package com.capstone.leukovision.data

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val lastDiagnosis: String? = null // Bisa null jika belum ada diagnosis
)