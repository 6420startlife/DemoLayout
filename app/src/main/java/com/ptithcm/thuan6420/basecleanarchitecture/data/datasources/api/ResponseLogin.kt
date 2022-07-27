package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api

import java.util.*

data class ResponseLogin(
    val data: Data,
    val message: String,
    val status: Int
)

data class Data(
    val email: String,
    val id: Int,
    val name: String,
    val phone_number: String
)