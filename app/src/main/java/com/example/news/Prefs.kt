package com.example.news

import android.content.Context
import android.text.TextWatcher
import com.example.news.ui.fourthTab.FourthFragment

class Prefs(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    fun saveState() {
        preferences.edit().putBoolean("isShown", true).apply()
    }

    fun isShown(): Boolean {
        return preferences.getBoolean("isShown", false)

    }

    fun saveNames(toString: String) {
        preferences.edit().putString("name", null).apply()
    }

    fun getName(): String? {
        return preferences.getString("name", null)
    }

    fun saveRegisterFragment() {
        r
    }
}