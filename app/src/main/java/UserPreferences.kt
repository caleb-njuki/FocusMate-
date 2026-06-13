package com.example.focusmatepro

import android.content.Context

class UserPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences("FocusMatePrefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, password: String) {
        prefs.edit()
            .putString("email", email)
            .putString("password", password)
            .apply()
    }

    fun userExists(email: String): Boolean {
        return prefs.getString("email", "") == email
    }

    fun validateUser(email: String, password: String): Boolean {
        val savedEmail = prefs.getString("email", "")
        val savedPassword = prefs.getString("password", "")

        return email == savedEmail && password == savedPassword
    }
}