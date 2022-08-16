package com.ptithcm.thuan6420.basecleanarchitecture.data.worker

import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.App.Companion.appContext
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.FoodWorker
import java.util.*
import java.util.concurrent.TimeUnit

object WorkerHelper {
    var worker: PeriodicWorkRequest = PeriodicWorkRequestBuilder<FoodWorker>(1, TimeUnit.MINUTES)
        .build()

    private fun initWorker() {
        WorkManager.getInstance(appContext).enqueue(worker)
    }

    private fun cancelWorker() {
        val workerId: UUID = worker.id
        WorkManager.getInstance(appContext).cancelWorkById(workerId)
    }
}