package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.UserBaseFragmentView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.viewmodel.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.OnSingleClickListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterFragment : UserBaseFragmentView(), OnSingleClickListener, DialogListener {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun setUiWhenLoading(status: Boolean) {
        binding.etEmailRegisterLayout.isEnabled = status
        binding.etPasswordRegisterLayout.isEnabled = status
        binding.etFullNameRegisterLayout.isEnabled = status
        binding.etPhoneNumberRegisterLayout.isEnabled = status
    }

    override val userViewModel: UserViewModel
        get() = initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener(this)
        binding.layoutRegisterFragment.setOnClickListener(this)
        binding.tvToLogin.setOnClickListener(this)

    }

    override fun setFormValid(): Job = lifecycleScope.launch {
        userViewModel.initFormRegisterValid()
    }

    override fun setValidate() {
        userViewModel.errorEmail.observe(viewLifecycleOwner) {
            binding.etEmailRegisterLayout.error = userViewModel.errorEmail.value
        }
        userViewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.etPasswordRegisterLayout.error = userViewModel.errorPassword.value
        }
        userViewModel.errorFullName.observe(viewLifecycleOwner) {
            binding.etFullNameRegisterLayout.error = userViewModel.errorFullName.value
        }
        userViewModel.errorPhoneNumber.observe(viewLifecycleOwner) {
            binding.etPhoneNumberRegisterLayout.error = userViewModel.errorPhoneNumber.value
        }
    }

    override fun doWhenShowSuccessDialog() {
        navigateToLogin()
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }

    override suspend fun onClicked(v: View?) {
        when (v) {
            binding.btnRegister -> userViewModel.register()
            binding.layoutRegisterFragment -> closeKeyBoard()
            binding.tvToLogin -> navigateToLogin()
        }
    }

    override fun onClickButtonOK() {
        navigateToLogin()
    }
}