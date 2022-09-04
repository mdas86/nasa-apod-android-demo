package com.example.nasaapod.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.nasaapod.base.BaseFragment
import com.example.nasaapod.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    companion object {
        const val NIGHT_MODE = "nightMode"
    }
    private var isNightModeOn = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefDarkMode: SharedPreferences =
            requireActivity().getSharedPreferences(NIGHT_MODE, Context.MODE_PRIVATE)
        val editorDarkMode = prefDarkMode.edit()
        isNightModeOn = prefDarkMode.getBoolean(NIGHT_MODE, false)

        binding.apply {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                enableDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                enableDarkMode.isChecked = false
            }

            enableDarkMode.setOnClickListener {
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editorDarkMode.putBoolean(NIGHT_MODE, false)
                    enableDarkMode.isChecked = false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editorDarkMode.putBoolean(NIGHT_MODE, true)
                    enableDarkMode.isChecked = true
                }
                editorDarkMode.apply()
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)
}