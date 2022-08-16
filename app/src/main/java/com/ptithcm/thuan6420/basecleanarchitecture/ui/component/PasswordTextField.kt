package com.ptithcm.thuan6420.basecleanarchitecture.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.basecleanarchitecture.R

class PasswordTextField(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    private var etPassword: TextInputEditText
    private var etPasswordLayout: TextInputLayout
    private var typedArray: TypedArray

    init {
        inflate(context, R.layout.component_password_text_field, this)
        etPassword = findViewById(R.id.etPassword)
        etPasswordLayout = findViewById(R.id.etPasswordLayout)
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordTextField, 0, 0)
    }

    private fun isEmptyPassword() = etPassword.text.toString().isEmpty()

    fun isValidPassword(): Boolean {
        if (isEmptyPassword()) {
            etPasswordLayout.error = "Hãy nhập password"
            return false
        }
        val isValid = etPassword.text.toString().length >= 6
        if (isValid.not()) {
            etPasswordLayout.error = "Password phải lớn hơn hoặc bằng 6 ký tự"
            return false
        }
        etPasswordLayout.error = null
        return true
    }

    fun getText() = etPassword.text.toString()
}