package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model

import android.util.Patterns
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.Credentials
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.InvalidField
import java.util.regex.Pattern

data class User(var email : String, var password : String,
                var fullName : String? = "", var phoneNumber : String? = "") : IUser {
    override fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isValidPassword(): Boolean {
        return password.length >= 9
    }

    override fun isValidPhoneNumber(): Boolean {
        return Pattern.compile(Credentials.REGEX_PHONE_NUMBER).matcher(phoneNumber).matches()
    }

    fun isValidLogin(): List<InvalidField> {
        val invalidFields = listOf(InvalidField(1, null), InvalidField(2, null))
        if (email.isEmpty()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_EMPTY_EMAIL
        } else if (!isValidEmail()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_INVALID_EMAIL
        } else {
            invalidFields.get(0).message = null
        }

        if (password.isEmpty()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_EMPTY_PASSWORD
        } else if (!isValidPassword()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_INVALID_PASSWORD
        } else {
            invalidFields.get(1).message = null
        }
        return invalidFields
    }

    fun isValidRegister(): List<InvalidField> {
        val invalidFields = listOf(
            InvalidField(1, null),
            InvalidField(2, null),
            InvalidField(3, null),
            InvalidField(4, null)
        )

        if (email.isEmpty()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_EMPTY_EMAIL
        } else if (!isValidEmail()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_INVALID_EMAIL
        } else {
            invalidFields.get(0).message = null
        }

        if (password.isEmpty()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_EMPTY_PASSWORD
        } else if (!isValidPassword()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_INVALID_PASSWORD
        } else {
            invalidFields.get(1).message = null
        }

        if (fullName?.isEmpty() == true) {
            invalidFields.get(2).message = ConstantMessage.MESSAGE_EMPTY_FULL_NAME
        } else {
            invalidFields.get(2).message = null
        }

        if (phoneNumber?.isEmpty() == true) {
            invalidFields.get(3).message = ConstantMessage.MESSAGE_EMPTY_PHONE_NUMBER
        } else if (!isValidPhoneNumber()) {
            invalidFields.get(3).message = ConstantMessage.MESSAGE_INVALID_PHONE_NUMBER
        } else {
            invalidFields.get(3).message = null
        }

        return invalidFields
    }
}
