package com.ptithcm.thuan6420.basecleanarchitecture.data.network

class ApiHelper constructor(private val apiService: ApiService) {
    suspend fun login(email: String, password: String) = apiService.login(email, password)

    suspend fun register(email: String, password: String, fullName: String, phoneNumber: Number)
            = apiService.register(email, password, fullName, phoneNumber)

    suspend fun fetchFood(userId: Int) = apiService.fetchFood(userId)
}