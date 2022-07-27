package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.login

interface ILoginView {
    fun navigateToMain()
    fun navigateToRegister()
    fun navigateToForgotPassword()
    fun showErrorEmail(message : String?)
    fun showErrorPassword(message : String?)
}