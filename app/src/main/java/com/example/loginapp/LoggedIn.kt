package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class LoggedIn : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logged_in)
        val btn = findViewById<Button>(R.id.logout)
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?:return
        val isLogin = sharedPref.getString("Email","1")
        val email = intent.getStringExtra("email")
        btn.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(isLogin == "1")
        {
            var email = intent.getStringExtra("email")
            if(email != null){
                setText(email)
                with(sharedPref.edit()){
                    putString("Email",email)
                    apply()
                }
            }
        }
        else
        {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setText(email:String)
    {
        db = FirebaseFirestore.getInstance()
        db.collection("USERS").document(email).get()
            .addOnSuccessListener {
                    task->
                findViewById<TextView>(R.id.name).text = task.get("Name").toString()
                findViewById<TextView>(R.id.phone).text = task.get("Phone").toString()
                findViewById<TextView>(R.id.email).text = task.get("email").toString()

            }
    }
}