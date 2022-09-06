package com.ptithcm.thuan6420.basecleanarchitecture.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import dagger.assisted.AssistedInject

@HiltWorker
class FoodWorker @AssistedInject constructor(context: Context,
                                             workerParams: WorkerParameters,
                                             private val foodRepository: FoodRepository,
                                             private val userRepository: UserRepository,
                                             private val network: Network) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}