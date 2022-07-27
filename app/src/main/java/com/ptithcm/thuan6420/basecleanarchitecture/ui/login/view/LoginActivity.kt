package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import android.os.Bundle
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
    }
}