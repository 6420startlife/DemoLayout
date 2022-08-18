package com.ptithcm.thuan6420.basecleanarchitecture.data.network

import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.login.LoginResponse
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.login.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("menus/items")
    suspend fun fetchFood(
        @Header("user_id") user_id : Int) : Response<ResponseFood>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email : String?,
        @Field("password") password : String?
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email : String?,
        @Field("password") password : String?,
        @Field("name") name : String,
        @Field("phone_number") phoneNumber : Number
    ): Response<RegisterResponse>
}