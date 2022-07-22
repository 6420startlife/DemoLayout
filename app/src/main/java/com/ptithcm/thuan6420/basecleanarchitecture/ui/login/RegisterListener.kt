package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

interface RegisterListener {
    fun onClickLayout()
    fun onClickButtonRegister(invalidFields: List<InvalidField>?, email: String,
                              password: String, fullName : String, phoneNumber : String)
    fun onClickTvToRegister()
    fun findInvalidFields(email : String, password : String, fullName: String,
                          phoneNumber: String) : List<InvalidField>
}