package com.capstone.leukovision.ui.patients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.leukovision.data.Patient
import com.capstone.leukovision.databinding.ItemPatientBinding

class PatientAdapter : ListAdapter<Patient, PatientAdapter.PatientViewHolder>(PatientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient)
    }

    class PatientViewHolder(private val binding: ItemPatientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(patient: Patient) {
            binding.textPatientName.text = patient.name
            binding.textPatientAgeGender.text = "${patient.age} tahun, ${patient.gender}"
            binding.textPatientDiagnosis.text = "Diagnosis: ${patient.lastDiagnosis ?: "Belum ada"}"

            // Anda bisa menambahkan onClickListener di sini jika setiap item pasien bisa diklik
            // binding.root.setOnClickListener {
            //     // Lakukan sesuatu saat item diklik, misalnya navigasi ke detail pasien
            // }
        }
    }

    private class PatientDiffCallback : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }
    }
}