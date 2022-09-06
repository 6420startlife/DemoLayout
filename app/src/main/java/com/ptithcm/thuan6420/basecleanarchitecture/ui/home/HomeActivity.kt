package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}