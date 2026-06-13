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

        val userPrefs = UserPreferences(this)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter Email and Password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // First login = Register

            if (!userPrefs.userExists(email)) {

                userPrefs.saveUser(email, password)

                Toast.makeText(
                    this,
                    "Registration Successful",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                if (!userPrefs.validateUser(email, password)) {

                    Toast.makeText(
                        this,
                        "Wrong Password",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }
            }

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )

            finish()
        }
    }
}