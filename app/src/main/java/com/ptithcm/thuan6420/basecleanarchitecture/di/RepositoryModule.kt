package com.ptithcm.thuan6420.basecleanarchitecture.di

import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(apiHelper: ApiHelper,
                              userDao: UserDao,
                              appSharedPreferences: AppSharedPreferences): UserRepository {
        return UserRepository(apiHelper, userDao, appSharedPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideFoodRepository(apiHelper: ApiHelper,
                              appSharedPreferences: AppSharedPreferences): FoodRepository {
        return FoodRepository(apiHelper, appSharedPreferences)
    }
}