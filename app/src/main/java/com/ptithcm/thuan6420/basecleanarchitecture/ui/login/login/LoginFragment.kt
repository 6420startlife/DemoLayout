package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.KEY_EMAIL_FORGOT
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.BaseFragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidEmail
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.isValidPassword
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.HomeActivity
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener(this)
        binding.tvToRegister.setOnClickListener(this)
        binding.layoutLoginFragment.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)
    }

    private fun login() {
        if (binding.etEmailLoginLayout.isValidEmail()
                .not() || binding.etPasswordLoginLayout.isValidPassword().not()
        ) {
            return
        }
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        viewModel.login(email, password).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        hideProgressDialog()
                        showSuccessDialog(resource.message)
                    }
                    FAILED -> {
                        hideProgressDialog()
                        showErrorDialog(resource.message)
                    }
                    ERROR -> {
                        hideProgressDialog()
                        showErrorDialog(resource.message)
                        Log.e("T64", resource.message.toString())
                    }
                    LOADING -> {
                        closeKeyBoard()
                        showProgressDialog()
                    }
                }
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        navigateToMain()
    }

    private fun navigateToMain() {
        this.requireActivity().finish()
        this.requireActivity().startActivity(Intent(this.context, HomeActivity::class.java))
    }

    private fun navigateToRegister() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToForgotPassword() {
        val bundle = Bundle()
        bundle.putString(KEY_EMAIL_FORGOT, binding.etEmailLogin.text.toString().trim())
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_forgotPasswordFragment, bundle)
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when (v) {
            binding.btnLogin -> login()
            binding.layoutLoginFragment -> closeKeyBoard()
            binding.tvToRegister -> navigateToRegister()
            binding.tvForgot -> navigateToForgotPassword()
        }
    }
}