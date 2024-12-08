package com.capstone.leukovision.ui.result

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.leukovision.databinding.FragmentResultBinding
import com.capstone.leukovision.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.Locale

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!  // Binding untuk mengakses elemen UI
    private val resultViewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan URI gambar dari argumen
        val imageUri = arguments?.getString(EXTRA_IMAGE_URI)?.let { Uri.parse(it) }

        if (imageUri == null) {
            Toast.makeText(requireContext(), "Tidak ada gambar yang dipilih.", Toast.LENGTH_SHORT).show()
            requireActivity().finish() // Mengakhiri activity jika tidak ada gambar
            return
        } else {
            resultViewModel.setImageUri(imageUri)
        }

        resultViewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                Log.d("photoPicker", "showImage: $it")
                // Tampilkan gambar yang dipilih
                binding.resultImageView.setImageURI(it)  // Sesuaikan dengan ID dari XML
            }
        }

        val imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.d("photoPicker", "Error: $error")
                    Toast.makeText(requireContext(), "Gagal menganalisis gambar: $error", Toast.LENGTH_SHORT).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        val topResult = it[0]
                        val label = topResult.categories[0].label
                        val score = topResult.categories[0].score

                        fun Float.formatToString(): String {
                            return String.format(Locale.US, "%.2f%%", this * 100)
                        }
                        binding.resultTextView.text = "$label ${score.formatToString()}"  // Sesuaikan dengan ID dari XML
                    }
                }
            }
        )

        resultViewModel.imageUri.value?.let { uri ->
            imageClassifierHelper.classifyStaticImage(uri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghindari kebocoran memori
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
