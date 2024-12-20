package com.example.dicodingapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    private val preferences = SettingPreferences(getApplicationContext())
    private val _isDarkThemeEnabled = MutableLiveData<Boolean>()
    val isDarkThemeEnabled: LiveData<Boolean> get() = _isDarkThemeEnabled

    init {
        _isDarkThemeEnabled.value = preferences.getThemeSetting()
    }

    fun saveThemeSetting(isDarkTheme: Boolean) {
        preferences.saveThemeSetting(isDarkTheme)
        _isDarkThemeEnabled.value = isDarkTheme
    }
}