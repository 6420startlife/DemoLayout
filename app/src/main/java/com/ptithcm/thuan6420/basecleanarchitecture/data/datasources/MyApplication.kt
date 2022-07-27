package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import android.app.Application
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences
import com.google.gson.Gson

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UserLocalDataSource.init(applicationContext)
    }
}