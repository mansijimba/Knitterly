package com.example.knitterly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.knitterly.R
import com.example.knitterly.Repository.AuthRepoImpl
import com.example.knitterly.ViewModel.AuthViewModel
import com.example.knitterly.databinding.ActivitySignupBinding
import com.example.knitterly.model.Usermodel

class SignupActivity : AppCompatActivity() {
    lateinit var signupBinding: ActivitySignupBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)

        val repo = AuthRepoImpl()
        authViewModel = AuthViewModel(repo)

        signupBinding.butdone.setOnClickListener {
            val name: String = signupBinding.txtName.text.toString()
            val email: String = signupBinding.txtUsername.text.toString()
            val number: String = signupBinding.txtNumber.text.toString()
            val password: String = signupBinding.txt2Passsword.text.toString()
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.buyer -> {
                        Toast.makeText(this, "Buyer is selected", Toast.LENGTH_SHORT).show()
                    }
                    R.id.seller -> {
                        Toast.makeText(this, "Seller is selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (name.isEmpty() || email.isEmpty() || number.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val userModel = Usermodel("", name, email, number)

            authViewModel.signUp(email, password) { success, message, userId ->
                if (success) {
                    userId?.let {
                        addUserToDatabase(it, userModel)
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun addUserToDatabase(userId: String, userModel: Usermodel) {
        authViewModel.addUserToDatabase(userId, userModel) { success, message ->
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            if (success) {
                finish()
            }
        }
    }
}