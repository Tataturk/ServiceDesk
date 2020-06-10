package com.example.servicedesk.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.servicedesk.R
import com.example.servicedesk.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        initViews()
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
    }

    private fun initViews() {

        btn_login.setOnClickListener{
            signIn()
        }
    }

    private fun signIn()   {
        if(!validateForm()) {
            return
        }

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this)    { task ->
                if (task.isSuccessful)  {
                    // Sign-in successful
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.putExtra("user",user)
                    startActivity(intent)
                    finish()
                }
                else  {
                    // Sign-in failed
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun validateForm(): Boolean {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        var valid = true
        if (email.isBlank())  {
            etEmail.setError("Email empty")
            valid = false
        }
        if (password.isBlank()) {
            etPassword.setError(("Password empty"))
            valid = false
        }
        return valid
    }





}
