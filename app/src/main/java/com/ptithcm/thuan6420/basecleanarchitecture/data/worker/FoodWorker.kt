package com.ptithcm.thuan6420.basecleanarchitecture.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import javax.inject.Inject

class FoodWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    @Inject lateinit var foodRepository: FoodRepository
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var network: Network

    fun fetchData() = foodRepository.fetchFood(userId = userRepository.getUserFromLocal().id ?: 0,
        isConnected = { network.isConnected() },
        onComplete = { it?.let { data -> foodRepository.setFoodFromLocal(data) } })

    override suspend fun doWork(): Result {
        return try {
            Log.e("T64", "Gọi Worker")
            fetchData()
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}