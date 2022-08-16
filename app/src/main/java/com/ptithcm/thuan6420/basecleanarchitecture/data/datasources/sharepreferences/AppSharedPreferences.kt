package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences

import android.content.Context
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.App
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.APP_SHARED_PREFERENCES

object AppSharedPreferences {
    fun putSharedPreferencesValue(key: String?, value: String?) {
        val sharedPreferences = App.appContext.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSharedPreferencesValue(key: String?): String? {
        val sharedPreferences = App.appContext.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }
}