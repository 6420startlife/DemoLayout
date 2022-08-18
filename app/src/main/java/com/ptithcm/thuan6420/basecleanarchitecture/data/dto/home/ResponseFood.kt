package com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home
import com.google.gson.annotations.SerializedName


data class ResponseFood(
    @SerializedName("data")
    val data: ResponseListMenu,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)





