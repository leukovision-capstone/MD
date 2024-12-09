package com.capstone.leukovision.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import com.capstone.leukovision.R
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions
import org.tensorflow.lite.task.core.BaseOptions

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifierOptions.builder()
            .setScoreThreshold(0.5f) // Threshold untuk klasifikasi
            .setMaxResults(1) // Hanya ambil satu hasil teratas

        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4) // Jumlah thread untuk inferensi

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                "validation_model_with_metadata.tflite", // Nama model
                optionsBuilder.build()
            )
        } catch (e: Exception) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, "Error initializing ImageClassifier: ${e.message}")
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        val bitmap = loadBitmapFromUri(imageUri) ?: run {
            classifierListener?.onError(context.getString(R.string.failed_to_load_image))
            return
        }

        // Tidak perlu memeriksa bitmap != null di sini
        val tensorImage = TensorImage.fromBitmap(bitmap)
        val results = imageClassifier?.classify(tensorImage)
        classifierListener?.onResults(results, 0) // Ganti 0 dengan waktu inferensi jika diperlukan
    }
//
//    fun classifyStaticImage(imageUri: Uri) {
//        val bitmap = loadBitmapFromUri(imageUri) ?: run {
//            classifierListener?.onError(context.getString(R.string.failed_to_load_image))
//            return
//        }
//        // Pastikan bitmap tidak null sebelum melanjutkan
//        if (bitmap != null) {
//            val tensorImage = TensorImage.fromBitmap(bitmap)
//            val results = imageClassifier?.classify(tensorImage)
//            classifierListener?.onResults(results, 0) // Ganti 0 dengan waktu inferensi jika diperlukan
//        } else {
//            classifierListener?.onError(context.getString(R.string.failed_to_load_image))
//        }
//    }

//        // Mengonversi bitmap ke TensorImage
//        val tensorImage = TensorImage.fromBitmap(bitmap)
//
//        // Menjalankan inferensi
//        val results = imageClassifier?.classify(tensorImage)
//
//        // Mengirimkan hasil ke listener
//        classifierListener?.onResults(results, 0) // Ganti 0 dengan waktu inferensi jika diperlukan

    private fun loadBitmapFromUri(imageUri: Uri): Bitmap? {
        return try {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading bitmap from URI: ${e.message}")
            null
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>?, inferenceTime: Long)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}