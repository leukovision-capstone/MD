package com.capstone.leukovision.ui.result

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.capstone.leukovision.R
import com.capstone.leukovision.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultFragment : Fragment(), ImageClassifierHelper.ClassifierListener {

    private lateinit var imageResultPreview: ImageView
    private lateinit var tvResultDescription: TextView
    private lateinit var btnAnalyzeLeukemia: Button
    private lateinit var btnBackToHome: Button
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        imageResultPreview = view.findViewById(R.id.imageResultPreview)
        tvResultDescription = view.findViewById(R.id.tvResultDescription)
        btnAnalyzeLeukemia = view.findViewById(R.id.btnAnalyzeLeukemia)
        btnBackToHome = view.findViewById(R.id.btnBackToHome)

        // Ambil bitmap dari argumen
        val bitmap = arguments?.getParcelable<Bitmap>("image")
        val imageUri = arguments?.getParcelable<Uri>("imageUri") // Jika Anda menggunakan URI

        // Tampilkan gambar di ImageView
        imageResultPreview.setImageBitmap(bitmap)

        // Inisialisasi ImageClassifierHelper
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = this
        )

        // Jika Anda menggunakan URI, panggil classifyImage
        imageUri?.let {
            imageClassifierHelper.classifyStaticImage(it)
        }

        btnBackToHome.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

//        btnAnalyzeLeukemia.setOnClickListener {
//            // Logika untuk analisis sel leukimia
//            // Misalnya, Anda bisa memulai Activity baru untuk analisis lebih lanjut
//            // val intent = Intent(requireContext(), AnalysisActivity::class.java)
//            // startActivity(intent)
//        }

        return view
    }

    override fun onError(error: String) {
        // Tangani kesalahan, misalnya dengan menampilkan pesan kepada pengguna
        tvResultDescription.text = error
    }

    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
        // Tangani hasil klasifikasi
        results?.let {
            val classification = it[0] // Ambil hasil klasifikasi pertama
            val category = classification.categories[0] // Ambil kategori dengan probabilitas tertinggi

            // Tampilkan hasil klasifikasi
            tvResultDescription.text = if (category.score > 0.5) {
                "Gambar sukses tervalidasi: ${category.label}"
            } else {
                "Gambar tidak valid: Non-mikroskopis"
            }
        }
    }
}