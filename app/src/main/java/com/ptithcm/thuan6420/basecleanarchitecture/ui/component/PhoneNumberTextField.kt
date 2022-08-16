package com.ptithcm.thuan6420.basecleanarchitecture.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.REGEX_PHONE_NUMBER
import java.util.regex.Pattern

class PhoneNumberTextField(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    private var etPhoneNumber: TextInputEditText
    private var etPhoneNumberLayout: TextInputLayout
    private var typedArray: TypedArray
    init {
        inflate(context, R.layout.component_phone_number_text_field, this)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etPhoneNumberLayout = findViewById(R.id.etPhoneNumberLayout)
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhoneNumberTextField, 0, 0)
    }

    private fun isEmptyPhone() = etPhoneNumber.text.toString().isEmpty()

    fun isValidPhoneNumber(): Boolean {
        if (isEmptyPhone()) {
            etPhoneNumberLayout.error = "Hãy nhập số điện thoại"
            return false
        }
        val isValid = Pattern.compile(REGEX_PHONE_NUMBER)
            .matcher(etPhoneNumber.text.toString()).matches()
        if (isValid.not()) {
            etPhoneNumberLayout.error = "Số điện thoại không hợp lệ"
            return false
        }
        etPhoneNumberLayout.error = null
        return true
    }

    fun getText() = etPhoneNumber.text.toString()
}