package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api

data class ResponseLogin(
    val data: ResponseUser,
    val message: String,
    val status: Int
)