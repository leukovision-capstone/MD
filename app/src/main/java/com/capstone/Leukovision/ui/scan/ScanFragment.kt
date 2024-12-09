package com.capstone.leukovision.ui.scan

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.leukovision.R
import com.capstone.leukovision.ml.ValidationModelWithMetadata
import com.capstone.leukovision.ui.result.ResultFragment
import org.tensorflow.lite.support.image.TensorImage

class ScanFragment : Fragment() {

    private lateinit var imagePreview: ImageView
    private lateinit var btnOpenGallery: Button
    private lateinit var btnValidateImage: Button
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        imagePreview = view.findViewById(R.id.imagePreview)
        btnOpenGallery = view.findViewById(R.id.btnOpenGallery)
        btnValidateImage = view.findViewById(R.id.btnValidateImage)

        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        btnValidateImage.setOnClickListener {
            selectedImageUri?.let { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                imagePreview.setImageBitmap(bitmap)
                validateImage(bitmap)
            } ?: run {
                Toast.makeText(requireContext(), "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), GALLERY_PERMISSION_CODE)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageUri?.let { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                imagePreview.setImageBitmap(bitmap)
            }
        }
    }

    private fun validateImage(bitmap: Bitmap) {
        val model = ValidationModelWithMetadata.newInstance(requireContext())
        val image = TensorImage.fromBitmap(bitmap)
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList

        val resultFragment = ResultFragment()
        val resultBundle = Bundle()
        resultBundle.putParcelable("image", bitmap)
        resultBundle.putFloat("probability", probability[1].score) // Kelas 1: Mikroskopis
        resultFragment.arguments = resultBundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, resultFragment)
            .addToBackStack(null)
            .commit()

        model.close()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Izin diperlukan untuk mengakses galeri", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
        private const val GALLERY_PERMISSION_CODE = 1002
    }
}