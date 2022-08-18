package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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