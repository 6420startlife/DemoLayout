package com.ptithcm.thuan6420.basecleanarchitecture.data.dto.login

data class LoginResponse(
    val data: ResponseUser,
    val message: String,
    val status: Int
)