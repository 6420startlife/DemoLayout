package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view.register

interface IRegisterView {
    fun showErrorEmail(message : String?)
    fun showErrorPassword(message : String?)
    fun showErrorFullName(message : String?)
    fun showErrorPhoneNumber(message : String?)
    fun isValidValueToRegister(isValid : Boolean)
}