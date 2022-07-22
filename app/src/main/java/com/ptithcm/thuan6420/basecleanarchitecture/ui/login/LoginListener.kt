package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

interface LoginListener {
    fun onClickLayout()
    fun onClickButtonLogin(invalidFields: List<InvalidField>?, email: String, password: String)
    fun onClickTvToRegister()
    fun findInvalidFields(email : String, password : String) : List<InvalidField>
}