package com.example.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonlogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var emailErrorMessage: TextView
    private lateinit var passwordErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        init()
        registerListeners()

    }

    private fun init() {

        editTextEmail = findViewById(R.id.emailAddress)
        editTextPassword = findViewById(R.id.password)
        buttonlogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        emailErrorMessage = findViewById(R.id.emailErrorMessage)
        passwordErrorMessage = findViewById(R.id.passwordErrorMessage)
    }

    private fun registerListeners() {

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        buttonlogin.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            when {
                email.isEmpty() -> {
                    emailErrorMessage.text = "ეს ველი არ უნდა იყოს ცარიელი"

                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    passwordErrorMessage.text = "ეს ველი არ უნდა იყოს ცარიელი"

                    return@setOnClickListener
                }
                else -> FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, ProfileActivity::class.java))
                            finish()
                        } else {
                            emailErrorMessage.text = "პაროლი ან E-mail არასწორია"
                            passwordErrorMessage.text = "პაროლი ან E-mail არასწორია"
                        }
                    }
            }

        }

    }

}