package com.ptithcm.thuan6420.basecleanarchitecture.di

import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.worker.FoodWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    @Provides
    @Singleton
    fun provideFoodWorker(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<FoodWorker>(1, TimeUnit.MINUTES).build()
    }
}