package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api

import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginApi {
    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun login(
        @Field("email") email : String?,
        @Field("password") password : String?
    ): Call<ResponseLogin?>?

    @FormUrlEncoded
    @POST("/auth/register")
    suspend fun register(
        @Field("email") email : String?,
        @Field("password") password : String?,
        @Field("name") name : String,
        @Field("phone_number") phoneNumber : Number
    ): Call<ResponseLogin?>?
}