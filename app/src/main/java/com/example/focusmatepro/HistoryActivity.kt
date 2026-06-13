package com.example.focusmatepro

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val tvHistoryPlaceholder =
            findViewById<TextView>(R.id.tvHistoryPlaceholder)

        val prefs = getSharedPreferences("FocusMatePrefs", Context.MODE_PRIVATE)

        val history = prefs.getString(
            "history",
            "Your focus history appears here"
        )

        tvHistoryPlaceholder.text = history
    }
}