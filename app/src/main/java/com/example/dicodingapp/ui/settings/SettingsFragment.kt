package com.example.dicodingapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingapp.R
import com.example.dicodingapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)

        setupUI()
        observeThemeSetting()

        return binding.root
    }

    private fun setupUI() {
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    private fun observeThemeSetting() {
        viewModel.isDarkThemeEnabled.observe(viewLifecycleOwner, { isDarkTheme ->
            binding.themeSwitch.isChecked = isDarkTheme
        })
    }
}