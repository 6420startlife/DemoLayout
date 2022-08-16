package com.ptithcm.thuan6420.basecleanarchitecture.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.basecleanarchitecture.R

class EmailTextField(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    private var etEmail: TextInputEditText
    private var etEmailLayout: TextInputLayout
    private var typedArray: TypedArray
    init {
        inflate(context, R.layout.component_email_text_field, this)
        etEmail = findViewById(R.id.etEmail)
        etEmailLayout = findViewById(R.id.etEmailLayout)
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmailTextField, 0, 0)
    }

    private fun isEmptyEmail() = etEmail.text.toString().isEmpty()

    fun isValidEmail(): Boolean {
        if (isEmptyEmail()) {
            etEmailLayout.error = "Hãy nhập email"
            return false
        }
        val isValid = Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()
        if (isValid.not()) {
            etEmailLayout.error = "Email không hợp lệ"
            return false
        }
        etEmailLayout.error = null
        return true
    }

    fun getText() = etEmail.text.toString()
}