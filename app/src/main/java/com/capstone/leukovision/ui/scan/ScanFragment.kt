package dicoding.capstone.leukovision.ui.scan

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var scanViewModel: ScanViewModel

    // ActivityResultLauncher to open the gallery
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            scanViewModel.setSelectedImageUri(it.toString())
            binding.imageScan.setImageURI(it) // Display the selected image in the ImageView
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scanViewModel = ViewModelProvider(this).get(ScanViewModel::class.java)

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up the TextView with data from ViewModel
        val textView: TextView = binding.textScan
        scanViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up button click listeners
        setupButtonListeners()

        return root
    }

    private fun setupButtonListeners() {
        // Button to open gallery
        binding.buttonBukaGaleri.setOnClickListener {
            openGallery()
        }

        // Button to validate image
        binding.buttonValidasiGambar.setOnClickListener {
            // Validate the image and navigate to ResultFragment
            scanViewModel.validateImage()
            findNavController().navigate(R.id.action_scanFragment_to_resultFragment)
        }
    }

    private fun openGallery() {
        // Launch the gallery using ActivityResultLauncher
        galleryLauncher.launch("image/*") // Opens only image types
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }
}
