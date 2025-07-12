package com.capstone.leukovision.ui.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.leukovision.databinding.FragmentPatientsBinding

class PatientsFragment : Fragment() {

    private var _binding: FragmentPatientsBinding? = null
    private val binding get() = _binding!!

    private val patientsViewModel: PatientsViewModel by viewModels()
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatientsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeViewModel()

        return root
    }

    private fun setupRecyclerView() {
        patientAdapter = PatientAdapter()
        binding.recyclerViewPatients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = patientAdapter
        }
    }

    private fun observeViewModel() {
        patientsViewModel.patients.observe(viewLifecycleOwner) { patientsList -> // Mengubah nama parameter untuk kejelasan
            // PERBAIKAN: Gunakan .isEmpty() untuk List<Patient> yang non-nullable
            if (patientsList.isEmpty()) {
                binding.recyclerViewPatients.visibility = View.GONE
                binding.textEmptyState.visibility = View.VISIBLE
            } else {
                patientAdapter.submitList(patientsList)
                binding.recyclerViewPatients.visibility = View.VISIBLE
                binding.textEmptyState.visibility = View.GONE
            }
            // Pastikan progressBarPatients ada di fragment_patients.xml
            binding.progressBarPatients.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}