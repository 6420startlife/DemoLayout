package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller.IUserController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller.UserController
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.BaseFragmentView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.OnSingleClickListener

class RegisterFragment : BaseFragmentView(), IRegisterView, OnSingleClickListener {
    private lateinit var binding : FragmentRegisterBinding
    private val userController : IUserController = UserController(this,null,this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener(this)
        binding.layoutRegisterFragment.setOnClickListener(this)
        binding.tvToLogin.setOnClickListener(this)
    }

    override fun turnOnLoading() {
        binding.pbRegister.visibility = View.VISIBLE
    }

    override fun turnOffLoading() {
        binding.pbRegister.visibility = View.GONE
    }

    override fun navigateToLogin() {
        NavHostFragment.findNavController(this@RegisterFragment)
            .navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun showErrorEmail(message: String?) {
        binding.etEmailRegisterLayout.error = message
    }

    override fun showErrorPassword(message: String?) {
        binding.etPasswordRegisterLayout.error = message
    }

    override fun showErrorFullName(message: String?) {
        binding.etFullNameRegisterLayout.error = message
    }

    override fun showErrorPhoneNumber(message: String?) {
        binding.etPhoneNumberRegisterLayout.error = message
    }

    override suspend fun onClicked(v: View?) {
        when(v) {
            binding.btnRegister -> {
                val email = binding.etEmailRegister.text.toString().trim()
                val password = binding.etPasswordRegister.text.toString().trim()
                val fullName = binding.etFullNameRegister.text.toString().trim()
                val phoneNumber = binding.etPhoneNumberRegister.text.toString().trim()

                userController.onRegister(email, password, fullName, phoneNumber)
            }
            binding.layoutRegisterFragment -> closeKeyBoard()
            binding.tvToLogin -> navigateToLogin()
        }
    }
}