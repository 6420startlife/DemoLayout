package com.ptithcm.thuan6420.basecleanarchitecture.data.dto

import com.google.gson.annotations.SerializedName

data class ResponseMessage<T>(@SerializedName("data") val data: T,
                              val message: String,
                              val status: Int)