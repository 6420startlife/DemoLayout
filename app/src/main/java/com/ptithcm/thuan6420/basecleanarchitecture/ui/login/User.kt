package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

data class User (var email : String, var password : String,
                 var fullName : String? = "", var phoneNumber : String? = "")