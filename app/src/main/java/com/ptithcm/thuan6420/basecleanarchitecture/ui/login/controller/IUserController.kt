package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller


interface IUserController {
    fun isValidLogin(email: String, password: String)
    fun isValidRegister(email: String, password: String, fullName: String, phoneNumber: String)
    suspend fun onLogin(email : String, password : String)
    suspend fun onRegister(email : String, password : String, fullName : String, phoneNumber: String)
}