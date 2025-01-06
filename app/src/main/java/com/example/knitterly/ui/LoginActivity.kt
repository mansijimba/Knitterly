package com.example.knitterly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.knitterly.R
import com.example.knitterly.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        loadingUtils = LoadingUtils(this)

        setContentView(R.layout.activity_login)
        val repo = AuthRepoImpl()
        authViewModel = AuthViewModel(repo)

        loginBinding.butLogin.setOnClickListener {
            login()
        }
        loginBinding.signUp.setOnClickListener {
            var intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        loginBinding.forgetPassword.setOnClickListener {
            var intent = Intent(this@LoginActivity,ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    fun login(){
        loadingUtils.showLoading()
        var email: String = loginBinding.txtUsername.text.toString()
        var password: String = loginBinding.txtPasswords.text.toString()
        authViewModel.login(email, password) { success, message ->
            if (success) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                loadingUtils.dismiss()

                intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                loadingUtils.dismiss()
            }
        }

    }
}