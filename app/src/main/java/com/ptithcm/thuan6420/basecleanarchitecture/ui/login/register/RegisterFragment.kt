package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_EMPTY_FULL_NAME
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.*
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.util.isNotEmptyText
import com.ptithcm.thuan6420.basecleanarchitecture.util.isValidEmail
import com.ptithcm.thuan6420.basecleanarchitecture.util.isValidPassword
import com.ptithcm.thuan6420.basecleanarchitecture.util.isValidPhoneNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: UserViewModel by viewModels()

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

    private fun register() {
        if (binding.etEmailRegisterLayout.isValidEmail().not() ||
            binding.etPasswordRegisterLayout.isValidPassword().not() ||
            binding.etFullNameRegisterLayout.isNotEmptyText(MESSAGE_EMPTY_FULL_NAME).not() ||
            binding.etPhoneNumberRegisterLayout.isValidPhoneNumber().not()
        ) {
            return
        }
        val email = binding.etEmailRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString()
        val fullName = binding.etFullNameRegister.text.toString()
        val phoneNumber = binding.etPhoneNumberRegister.text.toString().trim().toLong()

        viewModel.register(email, password, fullName, phoneNumber).observe(this) {
            it?.let {
                submit(it.status, it.message, it.data)
            }
        }
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.btnRegister -> register()
            binding.layoutRegisterFragment -> closeKeyBoard()
            binding.tvToLogin -> navigateToLogin()
        }
    }
}