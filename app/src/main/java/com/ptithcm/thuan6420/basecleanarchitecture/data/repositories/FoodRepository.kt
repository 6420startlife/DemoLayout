package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseListMenu
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appSharedPreferences: AppSharedPreferences
) {

    fun getFoodFromLocal(): ResponseListMenu {
        val jsonListFood = appSharedPreferences.getSharedPreferencesValue(Constants.PREF_FOOD)
        return Gson().fromJson(jsonListFood, ResponseListMenu::class.java)
    }

    fun setFoodFromLocal(menus: ResponseListMenu) {
        val jsonListFood = Gson().toJson(menus)
        appSharedPreferences.putSharedPreferencesValue(Constants.PREF_FOOD, jsonListFood)
    }

    suspend fun fetchData(userId: Int) = apiHelper.fetchFood(userId)
}