package com.ptithcm.thuan6420.basecleanarchitecture.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.FoodSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.RetrofitBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository

class FoodWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val repository: FoodRepository = FoodRepository(ApiHelper(RetrofitBuilder.apiService))

    override suspend fun doWork(): Result {
        return try {
            Log.e("T64", "Gọi Worker")
            val response = repository.fetchFood(UserSharedPreferences().getUserFromLocal().id)
            response.body()?.let {
                FoodSharedPreferences().setFoodFromLocal(it)
            }
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}