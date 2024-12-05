package com.capstone.leukovision.ui.scan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.leukovision.R
import com.capstone.leukovision.ui.viewmodel.SharedViewModel
import com.capstone.leukovision.utils.ImageClassifier

class ScanFragment : Fragment() {

    private lateinit var titleTextView: TextView
    private lateinit var previewImageView: ImageView
    private lateinit var btnOpenGallery: Button
    private lateinit var analyzeButton: Button
    private var selectedImageUri: Uri? = null

    // Register the activity result launcher
    private lateinit var getImageLauncher: ActivityResultLauncher<Intent>
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_scan, container, false)

        // Initialize UI elements
        titleTextView = root.findViewById(R.id.titleTextView)
        previewImageView = root.findViewById(R.id.previewImageView)
        btnOpenGallery = root.findViewById(R.id.btn_open_gallery)
        analyzeButton = root.findViewById(R.id.analyzeButton)

        // Initialize the activity result launcher
        getImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    previewImageView.setImageURI(selectedImageUri)
                }
            }
        }

        // Set listener for open gallery button
        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        // Set listener for analyze button
        analyzeButton.setOnClickListener {
            if (selectedImageUri != null) {
                analyzeImage(selectedImageUri!!)
            } else {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getImageLauncher.launch(intent) // Use the launcher to start the activity
    }

    private fun analyzeImage(uri: Uri) {
        try {
            // Load image from URI using BitmapFactory
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

            // Validate image using TensorFlow
            val isValid = validateImageWithTensorFlow(bitmap)

            // Send result to ResultFragment
            sharedViewModel.updateScanResult(isValid.toString()) // Update ViewModel with the result
            // Navigate to ResultFragment (you may want to use Navigation Component)
            // Example: findNavController().navigate(R.id.action_scanFragment_to_resultFragment)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to analyze image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateImageWithTensorFlow(bitmap: Bitmap): Boolean {
        // Inisialisasi ImageClassifier
        val classifier = ImageClassifier("validation_model.tflite") // Pastikan untuk memberikan path yang benar

        // Lakukan analisis gambar dan dapatkan hasil
        val result = classifier.classifyImage(bitmap)

        // Kembalikan true jika hasil > 0.5, false jika tidak
        return result.any { it > 0.5 } // Misalkan kita memeriksa semua hasil
    }
}