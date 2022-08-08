package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login

interface ILoginView {
    fun showErrorEmail(message : String?)
    fun showErrorPassword(message : String?)
    fun isValidValueToLogin(isValid : Boolean)
}