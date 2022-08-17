package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.dto.login

data class ResponseLogin(
    val data: ResponseUser,
    val message: String,
    val status: Int
)