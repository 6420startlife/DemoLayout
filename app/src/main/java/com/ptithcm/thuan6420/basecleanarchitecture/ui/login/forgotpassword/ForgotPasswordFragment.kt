package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentForgotPasswordBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.BaseFragment
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.KEY_EMAIL_FORGOT

class ForgotPasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
        binding.layoutForgotPasswordFragment.setOnClickListener(this)

        val email = arguments?.getString(KEY_EMAIL_FORGOT)
        binding.etEmailForgotPassword.setText(email)
    }

    private fun navigateToLogin() {
        NavHostFragment.findNavController(this@ForgotPasswordFragment)
            .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnBack -> navigateToLogin()
            binding.btnReset -> navigateToLogin()
            binding.layoutForgotPasswordFragment -> closeKeyBoard()
        }
    }
}