package com.capstone.leukovision.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.leukovision.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()
        setupListeners()

        return root
    }

    private fun observeViewModel() {
        settingsViewModel.appVersion.observe(viewLifecycleOwner) { version ->
            binding.textAppVersion.text = version
        }

        settingsViewModel.notificationsEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.switchNotifications.isChecked = isEnabled
        }
    }

    private fun setupListeners() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleNotifications(isChecked)
            val message = if (isChecked) "Notifikasi diaktifkan" else "Notifikasi dinonaktifkan"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener {
            settingsViewModel.performLogout()
            // Setelah logout, Anda mungkin ingin menavigasi ke layar login
            // Contoh: findNavController().navigate(R.id.action_settingsFragment_to_loginActivity)
            Toast.makeText(context, "Logout berhasil!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}