package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding
import kotlinx.coroutines.*

class RegisterViewImp(inflater : LayoutInflater, container : ViewGroup?) : RegisterView, View.OnClickListener, TextWatcher {
    private var binding : FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
    private var registerListener : RegisterListener? = null

    init {
        binding.etEmailRegister.addTextChangedListener(this)
        binding.etPasswordRegister.addTextChangedListener(this)
        binding.etFullNameRegister.addTextChangedListener(this)
        binding.etPhoneNumberRegister.addTextChangedListener(this)

        binding.tvToLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.layoutRegisterFragment.setOnClickListener(this)
    }
    override fun getRootView() = binding.root

    override fun setListener(listener: RegisterListener) {
        registerListener = listener
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnRegister -> {
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    binding.btnRegister.isEnabled = false
                    delay(500)
                    binding.btnRegister.isEnabled = true
                }
                val email = binding.etEmailRegister.text.toString().trim()
                val password = binding.etPasswordRegister.text.toString().trim()
                val fullName = binding.etFullNameRegister.text.toString().trim()
                val phoneNumber = binding.etPhoneNumberRegister.text.toString().trim()
                val invalidFields = registerListener?.findInvalidFields(email, password, fullName, phoneNumber)
                registerListener?.onClickButtonRegister(invalidFields, email, password, fullName, phoneNumber)
            }
            binding.layoutRegisterFragment -> registerListener?.onClickLayout()
            binding.tvToLogin -> registerListener?.onClickTvToRegister()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val email = binding.etEmailRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString().trim()
        val fullName = binding.etFullNameRegister.text.toString().trim()
        val phoneNumber = binding.etPhoneNumberRegister.text.toString().trim()
        val invalidFields = registerListener?.findInvalidFields(email, password, fullName, phoneNumber)
        for (invalidField in invalidFields!!) {
            setErrorMessageFields(invalidField)
        }
    }

    fun setErrorMessageFields(invalidField : InvalidField) {
        when(invalidField.idField) {
            UserController().ID_EMAIL_FIELD -> binding.etEmailRegisterLayout.error = invalidField.message
            UserController().ID_PASSWORD_FIELD -> binding.etPasswordRegisterLayout.error = invalidField.message
            UserController().ID_FULLNAME_FIELD -> binding.etFullNameRegisterLayout.error = invalidField.message
            UserController().ID_PHONENUMBER_FIELD -> binding.etPhoneNumberRegisterLayout.error = invalidField.message
        }
    }
}