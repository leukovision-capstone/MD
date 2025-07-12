package com.capstone.leukovision.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.leukovision.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeViewModel()

        // Memuat data saat fragment dibuat
        historyViewModel.loadHistory()

        return root
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter()
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }

    private fun observeViewModel() {
        historyViewModel.historyItems.observe(viewLifecycleOwner) { historyItems ->
            if (historyItems.isNullOrEmpty()) {
                binding.recyclerViewHistory.visibility = View.GONE
                binding.textEmptyStateHistory.visibility = View.VISIBLE
            } else {
                historyAdapter.submitList(historyItems)
                binding.recyclerViewHistory.visibility = View.VISIBLE
                binding.textEmptyStateHistory.visibility = View.GONE
            }
            // Sembunyikan progress bar setelah data dimuat
            binding.progressBarHistory.visibility = View.GONE
        }

        // Anda bisa tambahkan observer untuk status loading/error dari ViewModel jika diimplementasikan
        // historyViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
        //     binding.progressBarHistory.visibility = if (isLoading) View.VISIBLE else View.GONE
        // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}