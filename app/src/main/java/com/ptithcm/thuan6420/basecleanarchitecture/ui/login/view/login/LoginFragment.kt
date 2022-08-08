package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.view.MainActivity
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.UserBaseFragmentView
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.viewmodel.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.KEY_EMAIL_FORGOT
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.OnSingleClickListener
import kotlinx.coroutines.Job

class LoginFragment : UserBaseFragmentView(), OnSingleClickListener, DialogListener {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override val userViewModel: UserViewModel
        get() = initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener(this)
        binding.tvToRegister.setOnClickListener(this)
        binding.layoutLoginFragment.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)

    }

    override fun setFormValid(): Job = lifecycleScope.launchWhenCreated {
        userViewModel.initFormLoginValid()
    }

    override fun setValidate() {
        userViewModel.errorEmail.observe(viewLifecycleOwner) {
            binding.etEmailLoginLayout.error = userViewModel.errorEmail.value
        }
        userViewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.etPasswordLoginLayout.error = userViewModel.errorPassword.value
        }
    }

    override fun setUiWhenLoading(status: Boolean) {
        binding.etEmailLogin.isEnabled = status
        binding.etPasswordLogin.isEnabled = status
    }

    override fun doWhenShowSuccessDialog() {
        navigateToMain()
    }

    private fun navigateToMain() {
        this.requireActivity().finishAffinity()
        this.requireActivity().startActivity(Intent(this.context, MainActivity::class.java))
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

    override suspend fun onClicked(v: View?) {
        when (v) {
            binding.btnLogin -> userViewModel.login()
            binding.layoutLoginFragment -> closeKeyBoard()
            binding.tvToRegister -> navigateToRegister()
            binding.tvForgot -> navigateToForgotPassword()
        }
    }

    override fun onClickButtonOK() {
        navigateToMain()
    }
}