package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
         btn = findViewById<Button>(R.id.Register)

        btn.setOnClickListener{
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        val emailEditText = findViewById<EditText>(R.id.Email)
        val passwordEditText = findViewById<EditText>(R.id.Password)
        val loginButton = findViewById<Button>(R.id.Login)

        loginButton.setOnClickListener {
            if (checking(emailEditText, passwordEditText)) {
                // Proceed with login logic, for example:
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
                        var intent = Intent(this,LoggedIn::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checking(emailEditText: EditText, passwordEditText: EditText): Boolean {
        val email = emailEditText.text.toString().trim { it <= ' ' }
        val password = passwordEditText.text.toString().trim { it <= ' ' }

        return email.isNotEmpty() && password.isNotEmpty()
    }
}