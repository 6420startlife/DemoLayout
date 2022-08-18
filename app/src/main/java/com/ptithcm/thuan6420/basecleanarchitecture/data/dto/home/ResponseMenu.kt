package com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home

import com.google.gson.annotations.SerializedName

data class ResponseMenu(
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val products: List<ResponseProduct>,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_name")
    val userName: String
)