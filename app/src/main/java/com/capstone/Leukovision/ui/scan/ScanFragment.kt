package com.capstone.Leukovision.ui.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.capstone.Leukovision.R
import kotlinx.android.synthetic.main.fragment_scan.*

class ScanFragment : Fragment() {

    // Register activity result for opening gallery
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // Show the selected image (example: set it to ImageView or process further)
                selectedImageView.setImageURI(it)
                scanResult.text = "Gambar berhasil dipilih"
            } ?: run {
                scanResult.text = "Tidak ada gambar yang dipilih"
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up button to open gallery
        scanButton.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        // Launch gallery to pick an image
        galleryLauncher.launch("image/*")
    }
}
