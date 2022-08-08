package com.ptithcm.thuan6420.basecleanarchitecture.ui.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityMainBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.presenter.HomeContact

class MainActivity : AppCompatActivity(), HomeContact.ViewInterface {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
    }

    override fun onLoading(status: Boolean) {

    }
}