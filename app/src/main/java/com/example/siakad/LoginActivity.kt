package com.example.siakad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etNpm = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNpm)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)

        btnLogin.setOnClickListener {
            val npm = etNpm.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (npm.isEmpty() || password.isEmpty()) {
                android.widget.Toast.makeText(this, "NPM dan Password tidak boleh kosong!", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (npm == "230201055" && password == "123456") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                android.widget.Toast.makeText(this, "NPM atau Password salah!", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}
