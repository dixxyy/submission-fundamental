package com.example.dicodingapp.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SettingPreferences(context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveThemeSetting(isDarkTheme: Boolean) {
        preferences.edit().putBoolean(KEY_THEME_SETTING, isDarkTheme).apply()
    }

    fun getThemeSetting(): Boolean {
        return preferences.getBoolean(KEY_THEME_SETTING, false)
    }

    companion object {
        private const val KEY_THEME_SETTING = "theme_setting"
    }
}