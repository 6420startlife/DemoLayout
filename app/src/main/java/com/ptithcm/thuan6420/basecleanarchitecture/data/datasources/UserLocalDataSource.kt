package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.PREF_REGISTERED_USER

class UserLocalDataSource() {

    fun getUserFromLocal(): User {
        val jsonUser = AppSharedPreferences.getSharedPreferencesValue(PREF_REGISTERED_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User) {
        val jsonUser = Gson().toJson(user)
        AppSharedPreferences.putSharedPreferencesValue(PREF_REGISTERED_USER, jsonUser)
    }

    companion object {
        private var Instance: UserLocalDataSource? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UserLocalDataSource()
        }
    }
}