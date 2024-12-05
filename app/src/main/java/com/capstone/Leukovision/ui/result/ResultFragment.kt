package com.capstone.leukovision.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.capstone.leukovision.R
import com.capstone.leukovision.ui.viewmodel.SharedViewModel

class ResultFragment : Fragment() {

    private lateinit var resultTextView: TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        resultTextView = root.findViewById(R.id.resultTextView)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari argumen
        val isValid = arguments?.getBoolean("isValid") ?: false
        displayResult(isValid)

        // Mengamati perubahan pada hasil pemindaian
        sharedViewModel.scanResult.observe(viewLifecycleOwner, Observer { result ->
            // Tampilkan hasil pemindaian di UI
            displayResult(result.toBoolean())
        })
    }

    private fun displayResult(isValid: Boolean) {
        // Tampilkan hasil analisis
        resultTextView.text = if (isValid) {
            "Gambar valid mikroskopis"
        } else {
            "Gambar tidak valid"
        }
    }
}