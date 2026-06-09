package com.example.focusmatepro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Simple validation check before transitioning
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both Email and Password", Toast.LENGTH_SHORT).show()
            } else {
                // CHANGED HERE: Now moving cleanly to DashboardActivity instead of MainActivity
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish() // Closes LoginActivity so clicking 'Back' doesn't return here
            }
        }
    }
}