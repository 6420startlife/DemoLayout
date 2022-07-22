package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

class LoginViewImp(inflater : LayoutInflater, container: ViewGroup?) : LoginView, View.OnClickListener, TextWatcher {
    private var binding : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
    private var loginListener : LoginListener? = null

    init {
        binding.etEmailLogin.addTextChangedListener(this)
        binding.etPasswordLogin.addTextChangedListener(this)

        binding.tvToRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.layoutLoginFragment.setOnClickListener(this)
    }

    override fun getRootView() = binding.root

    override fun setListener(listener: LoginListener) {
        loginListener = listener
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnLogin -> {
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    binding.btnLogin.isEnabled = false
                    delay(500)
                    binding.btnLogin.isEnabled = true
                }
                val email = binding.etEmailLogin.text.toString().trim()
                val password = binding.etPasswordLogin.text.toString().trim()
                val invalidFields = loginListener?.findInvalidFields(email, password)
                loginListener?.onClickButtonLogin(invalidFields, email, password)
            }
            binding.layoutLoginFragment -> loginListener?.onClickLayout()
            binding.tvToRegister -> loginListener?.onClickTvToRegister()
        }
    }

    fun setErrorMessageFields(invalidField : InvalidField) {
        when(invalidField.idField) {
            UserController().ID_EMAIL_FIELD -> binding.etEmailLoginLayout.error = invalidField.message
            UserController().ID_PASSWORD_FIELD -> binding.etPasswordLoginLayout.error = invalidField.message
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val email = binding.etEmailLogin.text.toString().trim()
        val password = binding.etPasswordLogin.text.toString().trim()
        val invalidFields = loginListener?.findInvalidFields(email, password)
        for (invalidField in invalidFields!!) {
            setErrorMessageFields(invalidField)
        }
    }
}