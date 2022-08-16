package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper

class FoodRepository(private val apiHelper: ApiHelper) {
    suspend fun fetchFood(userId: Int) = apiHelper.fetchFood(userId)
}