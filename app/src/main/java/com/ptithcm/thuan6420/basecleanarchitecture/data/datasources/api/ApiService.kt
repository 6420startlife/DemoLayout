package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.login.ResponseLogin
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.login.ResponseRegister
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
    ): Response<ResponseLogin>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email : String?,
        @Field("password") password : String?,
        @Field("name") name : String,
        @Field("phone_number") phoneNumber : Number
    ): Response<ResponseRegister>
}