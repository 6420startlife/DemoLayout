package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.LoginApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginNetworkDataSource {
    private val gson : Gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit : Retrofit = Retrofit.Builder().baseUrl("http://118.69.77.23:3002")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val loginApi : LoginApi = retrofit.create(LoginApi::class.java)
    companion object{
        private var Instance : LoginNetworkDataSource? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = LoginNetworkDataSource()
        }
    }
}