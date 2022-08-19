package com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences

import android.content.Context
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.APP_SHARED_PREFERENCES

class AppSharedPreferences constructor(val context: Context) {
    fun putSharedPreferencesValue(key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSharedPreferencesValue(key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(
            APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }
}