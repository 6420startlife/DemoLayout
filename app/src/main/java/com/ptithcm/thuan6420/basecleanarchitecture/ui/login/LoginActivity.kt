package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import androidx.navigation.Navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import android.os.Bundle
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        setControl()
        setEvent()
    }

    private fun setControl() {
        navController = findNavController(this, R.id.nav_host_fragment_content_main)
    }

    private fun setEvent() {}
}