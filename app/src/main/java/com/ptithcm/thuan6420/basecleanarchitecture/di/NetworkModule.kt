package com.ptithcm.thuan6420.basecleanarchitecture.di

import android.content.Context
import com.facebook.CallbackManager
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiService
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): Network {
        return Network(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelper((apiService))
    }

    @Provides
    @Singleton
    fun provideCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }
}