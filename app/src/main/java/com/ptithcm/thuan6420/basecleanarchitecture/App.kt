package com.ptithcm.thuan6420.basecleanarchitecture

import android.app.Application
import com.ptithcm.thuan6420.basecleanarchitecture.di.appModule
import com.ptithcm.thuan6420.basecleanarchitecture.di.repositoryModule
import com.ptithcm.thuan6420.basecleanarchitecture.di.viewModelModule
import com.ptithcm.thuan6420.basecleanarchitecture.di.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule, workerModule))
        }
    }
}