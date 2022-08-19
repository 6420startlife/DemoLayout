package com.ptithcm.thuan6420.basecleanarchitecture.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.PeriodicWorkRequestBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiService
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.AppDatabase
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.worker.FoodWorker
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.FoodViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

private fun provideNetworkConnectivity(context: Context) = Network(context)

private fun provideRetrofit() = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

private fun provideApiHelper(apiService: ApiService) = ApiHelper((apiService))

private fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

private fun provideAppDatabase(application: Application) =
    Room.databaseBuilder(application.applicationContext,
        AppDatabase::class.java,
        "my_db")
        .build()

private fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

private fun provideAppSharedPreferences(context: Context) = AppSharedPreferences(context)

val appModule = module {
    single { provideNetworkConnectivity(androidContext()) }
    single { provideRetrofit() }
    single { provideApiService(get()) }
    single { provideApiHelper(get()) }
    single { provideCoroutineContext() }
    single { provideAppDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideAppSharedPreferences(androidContext()) }
}

val repositoryModule = module {
    single { UserRepository(get(), get(), get(), get()) }
    single { FoodRepository(get(), get(),get()) }
}

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { FoodViewModel(get(), get(), get()) }
}

private fun provideFoodWorker() = PeriodicWorkRequestBuilder<FoodWorker>(1, TimeUnit.MINUTES).build()

val workerModule = module {
    single { provideFoodWorker() }
    single { FoodWorker(androidContext(), get(), get(), get(), get())}
}

