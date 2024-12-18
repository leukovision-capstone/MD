package dicoding.capstone.leukovision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dicoding.capstone.leukovision.ui.patients.Patient

class PatientAdapter(private val patients: List<Patient>) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int = patients.size

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.patientName)
        private val ageTextView: TextView = itemView.findViewById(R.id.patientAge)
        private val genderTextView: TextView = itemView.findViewById(R.id.patientGender) // Tambahkan sesuai ID network layout
        private val addressTextView: TextView = itemView.findViewById(R.id.patientAddress) // Tambahkan sesuai ID network layout

        fun bind(patient: Patient) {
            // Format teks sesuai kebutuhan
            nameTextView.text = "Name: ${patient.name}"
            ageTextView.text = "Age: ${patient.age}"
            genderTextView.text = "Gender: ${patient.gender}"
            addressTextView.text = "Address: ${patient.address}"
        }
    }
}