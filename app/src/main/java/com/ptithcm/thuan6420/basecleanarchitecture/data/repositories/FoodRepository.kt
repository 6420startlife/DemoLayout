package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FoodRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appSharedPreferences: AppSharedPreferences,
    private val coroutineContext: CoroutineContext
) {

    fun getFoodFromLocal(): ResponseFood {
        val jsonListFood = appSharedPreferences.getSharedPreferencesValue(Constants.PREF_FOOD)
        return Gson().fromJson(jsonListFood, ResponseFood::class.java)
    }

    fun setFoodFromLocal(food: ResponseFood) {
        val jsonListFood = Gson().toJson(food)
        appSharedPreferences.putSharedPreferencesValue(Constants.PREF_FOOD, jsonListFood)
    }

    @WorkerThread
    fun fetchFood(
        userId: Int,
        isConnected: () -> Boolean,
        onComplete: (ResponseFood?) -> Unit
    ) = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            if (isConnected().not()) {
                val localData = getFoodFromLocal()
                emit(Resource.success(data = localData, message = null))
            } else {
                val response = apiHelper.fetchFood(userId)
                response.apply {
                    if (isSuccessful.not()) {
                        emit(Resource.failed(data = null, message = message()))
                    }
                    if (body()?.status == 200) {
                        onComplete(body())
                        val responseFood = body()?.data
                        emit(Resource.success(data = responseFood, message = null))
                    } else {
                        emit(Resource.failed(data = null, message = body()?.message))
                    }
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
        }
    }
}