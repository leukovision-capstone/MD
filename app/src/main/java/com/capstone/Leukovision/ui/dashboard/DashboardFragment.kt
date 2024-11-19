package com.capstone.Leukovision.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.Leukovision.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial values or listeners for your UI components
        greetingText.text = getString(R.string.greeting_text, "dokter 👋") // Example: dynamic greeting
        subtitleText.text = getString(R.string.subtitle_text)

        // Example: set data for total patients
        totalPatients.text = getString(R.string.total_patients, 10)
        totalAnalysis.text = getString(R.string.total_analysis, 10)

        // Recommendations
        recommendationHealthy.text = getString(R.string.recommendation_healthy, 10)
        recommendationMedium.text = getString(R.string.recommendation_medium, 10)
        recommendationEmergency.text = getString(R.string.recommendation_emergency, 10)

        // Handle navigation or interactions
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle Home click
                }
                R.id.navigation_patients -> {
                    // Handle Patients click
                }
                R.id.navigation_histories -> {
                    // Handle Histories click
                }
                R.id.navigation_settings -> {
                    // Handle Settings click
                }
            }
            true
        }
    }
}
