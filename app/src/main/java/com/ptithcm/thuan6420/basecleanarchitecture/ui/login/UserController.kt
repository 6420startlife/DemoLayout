package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.util.Patterns
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.ui.ResponseUI
import com.ptithcm.thuan6420.basecleanarchitecture.ui.util.ConstantMessage
import com.ptithcm.thuan6420.basecleanarchitecture.ui.util.Credentials.REGEX_PHONE_NUMBER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class UserController {

    val NO_FIELD: Byte = 0
    val ID_EMAIL_FIELD: Byte = 1
    val ID_PASSWORD_FIELD: Byte = 2
    val ID_FULLNAME_FIELD: Byte = 3
    val ID_PHONENUMBER_FIELD: Byte = 4

    fun isLoginSuccess(email: String, password: String) : ResponseUI {
        if (!UserRepository().isMatchedUser(User(email, password))) {
            return ResponseUI(false, ConstantMessage.MESSAGE_ERROR_LOGIN)
        }
        return ResponseUI(true, ConstantMessage.MESSAGE_SUCCESS_LOGIN)
    }

    fun isRegisterSuccess(
        email: String, password: String,
        fullName: String, phoneNumber: String
    ): ResponseUI {
        val user = User(email, password, fullName, phoneNumber)
        if (UserRepository().isExistedUser(user)) {
            return ResponseUI(false, ConstantMessage.MESSAGE_EXISTS_EMAIL)
        }
        UserRepository().createUser(user)
        return ResponseUI(true, ConstantMessage.MESSAGE_SUCCESS_REGISTER)
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 9
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return Pattern.compile(REGEX_PHONE_NUMBER).matcher(phoneNumber).matches()
    }

    /**
     * Tìm input field điền giá trị không hợp lệ trên Login Screen
     * 0 - no field
     * 1 - field email
     * 2 - field password
     * send error message
     */
    fun findInvalidLoginFields(email: String, password: String): List<InvalidField> {
        val invalidFields = listOf(InvalidField(1, null), InvalidField(2, null))
        if (email.isEmpty()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_EMPTY_EMAIL
        } else if (!isValidEmail(email)) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_INVALID_EMAIL
        } else {
            invalidFields.get(0).message = null
        }

        if (password.isEmpty()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_EMPTY_PASSWORD
        } else if (!isValidPassword(password)) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_INVALID_PASSWORD
        } else {
            invalidFields.get(1).message = null
        }
        return invalidFields
    }

    /**
     * Tìm input field điền giá trị không hợp lệ trên Register Screen
     * 0 - no field
     * 1 - field email
     * 2 - field password
     * 3 - field full name
     * 4 - phone number
     * send error message
     */
    fun findInvalidRegisterFields(
        email: String, password: String,
        fullName: String, phoneNumber: String
    ): List<InvalidField> {
        val invalidFields = listOf(
            InvalidField(1, null),
            InvalidField(2, null),
            InvalidField(3, null),
            InvalidField(4, null)
        )

        if (email.isEmpty()) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_EMPTY_EMAIL
        } else if (!isValidEmail(email)) {
            invalidFields.get(0).message = ConstantMessage.MESSAGE_INVALID_EMAIL
        } else {
            invalidFields.get(0).message = null
        }

        if (password.isEmpty()) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_EMPTY_PASSWORD
        } else if (!isValidPassword(password)) {
            invalidFields.get(1).message = ConstantMessage.MESSAGE_INVALID_PASSWORD
        } else {
            invalidFields.get(1).message = null
        }

        if (fullName.isEmpty()) {
            invalidFields.get(2).message = ConstantMessage.MESSAGE_EMPTY_FULL_NAME
        } else {
            invalidFields.get(2).message = null
        }

        if (phoneNumber.isEmpty()) {
            invalidFields.get(3).message = ConstantMessage.MESSAGE_EMPTY_PHONE_NUMBER
        } else if (!isValidPhoneNumber(phoneNumber)) {
            invalidFields.get(3).message = ConstantMessage.MESSAGE_INVALID_PHONE_NUMBER
        } else {
            invalidFields.get(3).message = null
        }

        return invalidFields
    }

    companion object {
        private var Instance: UserController? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UserController()
        }
    }
}