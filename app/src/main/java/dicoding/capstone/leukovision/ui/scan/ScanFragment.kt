package dicoding.capstone.leukovision.ui.scan

import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dicoding.capstone.leukovision.R
import dicoding.capstone.leukovision.databinding.FragmentScanBinding
import dicoding.capstone.leukovision.network.RetrofitInstance
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var buttonBukaGaleri: Button
    private lateinit var buttonAnalisisGambar: Button
    private val pickImageRequestCode = 1001
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScanBinding.inflate(inflater, container, false)

        imageView = binding.imageScan
        buttonBukaGaleri = binding.buttonBukaGaleri
        buttonAnalisisGambar = binding.buttonAnalisisGambar

        buttonBukaGaleri.setOnClickListener {
            openGallery()
        }

        buttonAnalisisGambar.setOnClickListener {
            selectedImageUri?.let { uri ->
                uploadImage(uri)
            }
        }

        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImage(uri: Uri) {
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
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(MediaStore.Images.Media.DATA)
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath ?: ""
    }
}
