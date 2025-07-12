package com.capstone.leukovision.data

import java.util.Date

data class HistoryItem(
    val id: String,
    val imageUrl: String, // URL atau path lokal ke gambar hasil scan
    val diagnosisResult: String,
    val confidence: Float,
    val date: Date // Tanggal dan waktu scan/diagnosis
)