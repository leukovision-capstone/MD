package com.capstone.leukovision.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView // Tidak lagi diperlukan secara eksplisit jika menggunakan binding langsung
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Import untuk by viewModels()
import androidx.navigation.fragment.findNavController
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // Menggunakan !! operator karena kita yakin _binding tidak akan null antara onCreateView dan onDestroyView
    private val binding get() = _binding!!

    // Inisialisasi ViewModel menggunakan delegate by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout menggunakan View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mengamati LiveData dari ViewModel untuk memperbarui UI
        homeViewModel.text.observe(viewLifecycleOwner) { welcomeText ->
            binding.textHome.text = welcomeText // Menggunakan binding langsung untuk TextView
        }
        // Contoh: jika Anda punya LiveData lain di ViewModel
        // homeViewModel.patientCount.observe(viewLifecycleOwner) { count ->
        //     binding.textPatientCount.text = "Jumlah Pasien: $count"
        // }


        // Menyiapkan listener untuk navigasi internal dari dashboard
        binding.cardPatients.setOnClickListener {
            // Menggunakan ID action yang benar dari mobile_navigation.xml
            findNavController().navigate(R.id.action_navigation_home_to_navigation_patients)
        }

        binding.cardHistory.setOnClickListener {
            // Menggunakan ID action yang benar dari mobile_navigation.xml
            findNavController().navigate(R.id.action_navigation_home_to_navigation_history)
        }

        binding.buttonScan.setOnClickListener {
            // Menggunakan ID action yang benar dari mobile_navigation.xml
            findNavController().navigate(R.id.action_navigation_home_to_navigation_scan)
        }

        // Jika Anda memiliki data dashboard yang perlu dimuat saat Fragment dibuat:
        // homeViewModel.loadDashboardData()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Penting untuk membersihkan binding saat view dihancurkan untuk menghindari memory leaks
        _binding = null
    }
}