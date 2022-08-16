package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import android.util.Log
import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.PREF_FOOD

class FoodLocalDataSource() {

    fun getFoodFromLocal(): ResponseFood {
        val jsonListFood = AppSharedPreferences.getSharedPreferencesValue(PREF_FOOD)
        return Gson().fromJson(jsonListFood, ResponseFood::class.java)
    }

    fun setFoodFromLocal(food: ResponseFood) {
        val jsonListFood = Gson().toJson(food)
        Log.e("T64", jsonListFood)
        AppSharedPreferences.putSharedPreferencesValue(PREF_FOOD, jsonListFood)
    }

    companion object {
        private var Instance: FoodLocalDataSource? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = FoodLocalDataSource()
        }
    }
}