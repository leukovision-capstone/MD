package com.capstone.leukovision.ui.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.leukovision.data.Patient // Pastikan import ini benar dan Patient.kt ada

class PatientsViewModel : ViewModel() {

    private val _patients = MutableLiveData<List<Patient>>()
    val patients: LiveData<List<Patient>> = _patients

    init {
        // Contoh data dummy.
        _patients.value = listOf(
            Patient("P001", "Budi Santoso", 45, "Pria", "Limfoblastik Akut"),
            Patient("P002", "Siti Aminah", 32, "Wanita", "Tidak Ada Leukimia"),
            Patient("P003", "Andi Wijaya", 60, "Pria", "Limfoblastik Kronis"),
            Patient("P004", "Dewi Lestari", 28, "Wanita", "Tidak Ada Leukimia"),
            Patient("P005", "Joko Susilo", 55, "Pria", "Limfoblastik Akut")
        )
    }

    fun refreshPatients() {
        // Placeholder untuk refresh atau API call
    }
}