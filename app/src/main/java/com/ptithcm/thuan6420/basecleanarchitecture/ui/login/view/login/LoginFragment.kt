package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding

import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller.IUserController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller.UserController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.KEY_EMAIL_FORGOT
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.BaseFragmentView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.OnSingleClickListener


class LoginFragment : BaseFragmentView(), ILoginView, OnSingleClickListener {
    private lateinit var binding : FragmentLoginBinding
    private val userController : IUserController = UserController(this,this,null)

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

    override fun turnOnLoading() {
        binding.pbLogin.visibility = View.VISIBLE
    }

    override fun turnOffLoading() {
        binding.pbLogin.visibility = View.GONE
    }

    override fun navigateToMain() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_mainActivity)
    }

    override fun navigateToRegister() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun showErrorEmail(message: String?) {
        binding.etEmailLoginLayout.error = message
    }

    override fun showErrorPassword(message: String?) {
        binding.etPasswordLoginLayout.error = message
    }

    override fun navigateToForgotPassword() {
        val bundle = Bundle()
        bundle.putString(KEY_EMAIL_FORGOT, binding.etEmailLogin.text.toString().trim())
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_forgotPasswordFragment, bundle)
    }

    override suspend fun onClicked(v: View?) {
        when(v) {
            binding.btnLogin -> {
                val email = binding.etEmailLogin.text.toString().trim()
                val password = binding.etPasswordLogin.text.toString().trim()

                userController.onLogin(email, password)
            }
            binding.layoutLoginFragment -> closeKeyBoard()
            binding.tvToRegister -> navigateToRegister()
            binding.tvForgot -> navigateToForgotPassword()
        }
    }
}