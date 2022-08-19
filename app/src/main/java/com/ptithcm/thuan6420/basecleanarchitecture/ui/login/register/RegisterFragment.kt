package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_EMPTY_FULL_NAME
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.*
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: UserViewModel by viewModel()

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
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        showSuccessDialog(resource.message)
                    }
                    Status.FAILED -> {
                        hideProgressDialog()
                        showErrorDialog(resource.message)
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        showErrorDialog(resource.message)
                        Log.e("T64", resource.message.toString())
                    }
                    Status.LOADING -> {
                        closeKeyBoard()
                        showProgressDialog()
                    }
                }
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