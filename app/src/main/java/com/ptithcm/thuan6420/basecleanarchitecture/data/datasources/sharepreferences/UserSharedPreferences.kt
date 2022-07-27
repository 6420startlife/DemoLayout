package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences

import android.content.Context
import android.content.SharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences

class UserSharedPreferences(context: Context) {
    private var context : Context = context
    fun putUserValue(key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(
            USER_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getUserValue(key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(
            USER_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }

    companion object {
        private const val USER_SHARED_PREFERENCES = "USER_SHARED_PREFERENCES"
    }
}