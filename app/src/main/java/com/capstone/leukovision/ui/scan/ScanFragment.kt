package com.capstone.leukovision.ui.scan

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentScanBinding
import com.capstone.leukovision.network.RetrofitInstance
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var buttonBukaGaleri: Button
    private lateinit var buttonAnalisisGambar: Button
    private var selectedImageUri: Uri? = null

    // --- PERBAIKAN UNTUK DEPRECATED startActivityForResult/onActivityResult ---
    // Deklarasikan ActivityResultLauncher di sini
    private val pickImageLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                imageView.setImageURI(selectedImageUri)
                buttonAnalisisGambar.isEnabled = true // Aktifkan tombol analisis
            } else {
                Toast.makeText(requireContext(), "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show()
                buttonAnalisisGambar.isEnabled = false // Nonaktifkan jika tidak ada gambar
            }
        }
    // ----------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScanBinding.inflate(inflater, container, false)

        imageView = binding.imageScan
        buttonBukaGaleri = binding.buttonBukaGaleri
        buttonAnalisisGambar = binding.buttonAnalisisGambar

        // Pastikan tombol analisis nonaktif saat pertama kali
        buttonAnalisisGambar.isEnabled = (selectedImageUri != null)

        buttonBukaGaleri.setOnClickListener {
            openGallery()
        }

        buttonAnalisisGambar.setOnClickListener {
            selectedImageUri?.let { uri ->
                uploadImage(uri)
            } ?: run {
                Toast.makeText(requireContext(), "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun openGallery() {
        // --- PERBAIKAN: Gunakan launcher baru ---
        pickImageLauncher.launch("image/*") // Meluncurkan aktivitas pemilihan gambar
        // ----------------------------------------
    }

    // --- PERBAIKAN: Hapus sepenuhnya fungsi onActivityResult yang deprecated ---
    // Anda tidak perlu lagi meng-override ini
    // override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //     super.onActivityResult(requestCode, resultCode, data)
    //     // Logika ini sekarang sudah dipindahkan ke lambda pickImageLauncher di atas
    // }
    // --------------------------------------------------------------------------

    private fun uploadImage(uri: Uri) {
        // Opsional: Tampilkan progress bar dan nonaktifkan tombol
        // binding.progressBar.visibility = View.VISIBLE
        buttonAnalisisGambar.isEnabled = false
        buttonBukaGaleri.isEnabled = false // Memperbaiki typo di sini

        val file = File(getRealPathFromURI(uri))
        val requestBody = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

        lifecycleScope.launch {
            try {
                val detectResponse = RetrofitInstance.apiService.detectImage(multipartBody)
                val analyzeResponse = RetrofitInstance.apiService.analyzeImage(multipartBody)

                val detectImage = BitmapFactory.decodeStream(detectResponse.byteStream())
                val analysisResult = analyzeResponse.data

                val bundle = Bundle().apply {
                    putParcelable("detect_image", detectImage)
                    putString("predicted_class", analysisResult.predicted_class)
                    putFloat("confidence", analysisResult.confidence)
                }

                findNavController().navigate(R.id.action_scanFragment_to_resultFragment, bundle)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error saat analisis: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                // Opsional: Sembunyikan progress bar dan aktifkan tombol kembali
                // binding.progressBar.visibility = View.GONE
                buttonAnalisisGambar.isEnabled = true
                buttonBukaGaleri.isEnabled = true
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(MediaStore.Images.Media.DATA)
        val filePath = if (columnIndex != null && !cursor.isNull(columnIndex)) {
            cursor.getString(columnIndex)
        } else {
            throw IllegalArgumentException("Could not get real path from URI: $uri")
        }
        cursor?.close()
        return filePath
    }
}