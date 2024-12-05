package com.capstone.leukovision.ui.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatientsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Patients Fragment"
    }
    val text: LiveData<String> = _text
}