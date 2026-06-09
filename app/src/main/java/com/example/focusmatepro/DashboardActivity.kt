package com.example.focusmatepro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Find the interactive layout buttons
        val btnManageTime = findViewById<Button>(R.id.btnManageTime)
        val btnMyHistory = findViewById<Button>(R.id.btnMyHistory)
        val btnMySchedule = findViewById<Button>(R.id.btnMySchedule)

        // 1. Navigation routing to the focus timer (MainActivity)
        btnManageTime.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 2. Opens the History Screen
        btnMyHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // 3. Opens the Schedule 8x8 Table Screen
        btnMySchedule.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
        }
    }
