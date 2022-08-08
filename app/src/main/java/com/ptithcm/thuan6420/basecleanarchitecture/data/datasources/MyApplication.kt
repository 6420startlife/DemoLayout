package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import android.app.Application
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.MyDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserLocalDataSource().init(applicationContext)
    }
}