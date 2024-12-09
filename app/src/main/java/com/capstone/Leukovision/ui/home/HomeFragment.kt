package com.capstone.leukovision.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mengatur TextView dengan data dari ViewModel
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Menambahkan click listener untuk navigasi
        binding.cardViewPatients.setOnClickListener { view ->
            navigateToPatients(view)
        }

        binding.cardViewHistory.setOnClickListener { view ->
            navigateToHistory(view)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToPatients(view: View) {
        // Logika untuk navigasi ke halaman pasien
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_homeFragment_to_patientsFragment)
    }

    private fun navigateToHistory(view: View) {
        // Logika untuk navigasi ke halaman analisis sejarah
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_homeFragment_to_historyFragment)
    }
}