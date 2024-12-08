package com.capstone.leukovision.ui.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentScanBinding // Pastikan ini sesuai dengan nama file XML Anda
import com.capstone.leukovision.ui.result.ResultFragment
import com.yalantis.ucrop.UCrop
import java.io.File

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observasi perubahan pada imageUri dari ViewModel
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let { uri: Uri ->  // Menyebutkan tipe data eksplisit
                binding.previewImageView.setImageURI(uri)
            }
        }

        binding.btnOpenGallery.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            viewModel.imageUri.value?.let { uri: Uri ->  // Tentukan tipe eksplisit untuk 'uri'
                analyzeImage(uri)
            } ?: run {
                showToast(getString(R.string.image_classifier_failed))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setImageUri(uri)
            showImage(uri)
        } else {
            Log.d("photoPicker", "No media selected")
            showToast(getString(R.string.unselect_image))
        }
    }

    private fun startCropActivity(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image.jpg"))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(224, 224)
            .start(requireContext(), this)
    }

    private fun showImage(uri: Uri) {
        Log.d("photoPicker", "showImage: $uri")
        startCropActivity(uri)
    }

    private fun analyzeImage(uri: Uri) {
        val intent = Intent(requireContext(), ResultFragment::class.java)
        intent.putExtra(ResultFragment.EXTRA_IMAGE_URI, uri.toString())  // Convert to String explicitly
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghindari kebocoran memori
    }
}
