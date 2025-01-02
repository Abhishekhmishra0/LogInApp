package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var btn: Button // Use lateinit for late initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // Initialize the button after setContentView
        btn = findViewById(R.id.continue_button)

        btn.setOnClickListener {
            if (checking()) {
                val email = findViewById<EditText>(R.id.email_input).text.toString()
                val password = findViewById<EditText>(R.id.password_input).text.toString()
                val name = findViewById<EditText>(R.id.name_input).text.toString()
                val phone = findViewById<EditText>(R.id.number_input).text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()
                            var intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter The Details", Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checking(): Boolean {
        val name = findViewById<EditText>(R.id.name_input).text.toString().trim { it <= ' ' }
        val phone = findViewById<EditText>(R.id.number_input).text.toString().trim { it <= ' ' }
        val email = findViewById<EditText>(R.id.email_input).text.toString().trim { it <= ' ' }
        val password = findViewById<EditText>(R.id.password_input).text.toString().trim { it <= ' ' }

        return name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
}
