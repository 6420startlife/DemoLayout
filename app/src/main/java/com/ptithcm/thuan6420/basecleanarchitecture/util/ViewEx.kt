package com.ptithcm.thuan6420.basecleanarchitecture.util

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_EMPTY_EMAIL
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_EMPTY_PASSWORD
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_EMPTY_PHONE_NUMBER
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_INVALID_EMAIL
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_INVALID_PASSWORD
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_INVALID_PHONE_NUMBER
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.REGEX_PHONE_NUMBER
import java.util.regex.Pattern

fun TextInputLayout.isValidEmail(): Boolean {
    val isValid = Patterns.EMAIL_ADDRESS.matcher(editText?.text.toString().trim()).matches()
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_EMAIL
        return false
    }
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_EMAIL
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isValidPassword(): Boolean {
    val isValid = editText?.text?.length!! >= 6
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_PASSWORD
        return false
    }
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_PASSWORD
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isValidPhoneNumber(): Boolean {
    val isValid = Pattern.compile(REGEX_PHONE_NUMBER)
        .matcher(editText?.text.toString().trim()).matches()
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = MESSAGE_EMPTY_PHONE_NUMBER
        return false
    }
    if (isValid.not()) {
        isErrorEnabled = true
        error = MESSAGE_INVALID_PHONE_NUMBER
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}

fun TextInputLayout.isNotEmptyText(message: String): Boolean {
    if (editText?.text?.isEmpty() == true) {
        isErrorEnabled = true
        error = message
        return false
    }
    isErrorEnabled = false
    error = null
    return true
}
