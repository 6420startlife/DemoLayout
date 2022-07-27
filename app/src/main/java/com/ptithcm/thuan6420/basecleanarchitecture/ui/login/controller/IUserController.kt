package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.controller

import android.widget.Button
import android.widget.ProgressBar

interface IUserController {
    fun onLogin(email : String, password : String)
    fun onRegister(email : String, password : String, fullName : String, phoneNumber: String)
}