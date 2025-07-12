package com.capstone.leukovision.ui.result

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.leukovision.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        // --- PERBAIKAN UNTUK DEPRECATED getParcelable ---
        val detectImage: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Untuk Android 13 (API 33) dan yang lebih baru
            arguments?.getParcelable("detect_image", Bitmap::class.java)
        } else {
            // Untuk API di bawah Android 13, tetap gunakan metode deprecated dan suppress warning
            @Suppress("DEPRECATION")
            arguments?.getParcelable("detect_image")
        }
        // ---------------------------------------------------

        val predictedClass = arguments?.getString("predicted_class") ?: "Tidak Diketahui"
        val confidence = arguments?.getFloat("confidence") ?: 0.0f

        detectImage?.let {
            binding.imageDetectionResult.setImageBitmap(it)
        }

        binding.textDiagnosis.text = predictedClass
        binding.textConfidence.text = String.format("%.2f%%", confidence * 100)

        return binding.root
    }
}