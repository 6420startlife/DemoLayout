package com.ptithcm.thuan6420.basecleanarchitecture.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.AppDatabase
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext,
            AppDatabase::class.java,
            "my_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppSharedPreferences(@ApplicationContext context: Context): AppSharedPreferences {
        return AppSharedPreferences(context)
    }
}