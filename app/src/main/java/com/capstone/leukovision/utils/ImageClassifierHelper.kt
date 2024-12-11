package dicoding.capstone.leukovision.utils

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class ImageClassifierHelper(
    context: Context,
    modelPath: String = "validation_model_with_metadata.tflite", // Replace with the actual model file name
    private val maxResults: Int = 1
) {
    private val imageClassifier: ImageClassifier

    init {
        // Configure the ImageClassifier options
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(maxResults)
            .build()

        // Initialize the ImageClassifier
        imageClassifier = ImageClassifier.createFromFileAndOptions(context, modelPath, options)
    }

    fun classifyImage(bitmap: Bitmap): MutableList<Classifications>? {
        val tensorImage = TensorImage.fromBitmap(bitmap)
        return imageClassifier.classify(tensorImage)
    }

    fun close() {
        imageClassifier.close()
    }
}

