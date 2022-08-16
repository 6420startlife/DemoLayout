package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api

import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}