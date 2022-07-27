package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model

interface IUser {
    fun isValidEmail() : Boolean
    fun isValidPassword() : Boolean
    fun isValidPhoneNumber() : Boolean
}