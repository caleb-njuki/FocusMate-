package com.example.focusmatepro

import android.content.Context

class HistoryPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences("HistoryPrefs", Context.MODE_PRIVATE)

    fun saveHistory(entry: String) {

        val oldHistory = prefs.getString("history", "")

        prefs.edit()
            .putString(
                "history",
                entry + "\n\n" + oldHistory
            )
            .apply()
    }

    fun getHistory(): String {

        return prefs.getString(
            "history",
            "Your history appears here"
        ) ?: "Your history appears here"
    }

}