package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register

interface IRegisterView {
    fun navigateToLogin()
    fun showErrorEmail(message : String?)
    fun showErrorPassword(message : String?)
    fun showErrorFullName(message : String?)
    fun showErrorPhoneNumber(message : String?)
}