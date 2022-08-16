package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.RetrofitBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.MyDatabase
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.BaseFragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModelFactory
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel

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
        setViewModel()
    }

    private fun register() {
        val email = binding.etEmailRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString()
        val fullName = binding.etFullNameRegister.text.toString().trim()
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

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
                MyDatabase.getInstance(this.requireActivity().application).userDao
            )
        )[UserViewModel::class.java]
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