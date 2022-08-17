package com.ptithcm.thuan6420.basecleanarchitecture

import kotlinx.coroutines.Dispatchers

object Constants {
    const val MESSAGE_ERROR_LOGIN = "Login Failed. Please try again"
    const val MESSAGE_ERROR_LOADING = "Loading failed"
    const val MESSAGE_SUCCESS_LOGIN = "Login Success"
    const val MESSAGE_SUCCESS_REGISTER = "Successfully registered"
    const val MESSAGE_EMPTY_EMAIL = "Email not null"
    const val MESSAGE_EMPTY_PASSWORD = "Password not null"
    const val MESSAGE_EMPTY_FULL_NAME = "Full name not null"
    const val MESSAGE_EMPTY_PHONE_NUMBER = "Phone number not null"
    const val MESSAGE_INVALID_EMAIL = "Invalid email address"
    const val MESSAGE_INVALID_PASSWORD = "Invalid password"
    const val MESSAGE_INVALID_PHONE_NUMBER = "Invalid phone number"
    const val MESSAGE_EXISTS_EMAIL = "Exists email address"
    const val MESSAGE_LOADING = "Loading ..."
    const val MESSAGE_ERROR_VALID = "Dữ liệu nhập vào không hợp lệ"
    const val DEFAULT_ERROR_MESSAGE = "Error Occurred!"

    const val REGEX_PHONE_NUMBER = "^[0]{1}\\d{9}$"
    const val BASE_URL = "http://118.69.77.23:3002/"
    val IO_DISPATCHER = Dispatchers.IO
    const val DEFAULT_INTERVAL = 500L

    const val KEY_EMAIL_FORGOT = "email_forgot"
    const val APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES"
    const val PREF_REGISTERED_USER = "PREF_REGISTERED_USER"
    const val PREF_FOOD = "PREF_FOOD"
}