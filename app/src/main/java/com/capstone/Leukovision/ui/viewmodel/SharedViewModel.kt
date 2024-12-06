package com.capstone.leukovision.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.leukovision.utils.ImageClassifier

class SharedViewModel : ViewModel() {
    private val _scanResult = MutableLiveData<Boolean>()
    val scanResult: LiveData<Boolean> get() = _scanResult

    private lateinit var imageClassifier: ImageClassifier

    fun classifyImage(bitmap: Bitmap, context: Context) {
        // Initialize the ImageClassifier
        imageClassifier = ImageClassifier(context, "model.tflite") // Replace with your model path

        // Classify the image
        val result = imageClassifier.classifyImage(bitmap)

        // Process the result (this is just an example, adjust according to your model's output)
        _scanResult.value = result[0] > 0.5 // Assuming a threshold for classification
    }
}