package dicoding.capstone.leukovision.ui.result

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dicoding.capstone.leukovision.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val detectImage = arguments?.getParcelable<Bitmap>("detect_image")
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
