package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences

import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.PREF_REGISTERED_USER

class UserSharedPreferences() {

    fun getUserFromLocal(): User {
        val jsonUser = AppSharedPreferences.getSharedPreferencesValue(PREF_REGISTERED_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User) {
        val jsonUser = Gson().toJson(user)
        AppSharedPreferences.putSharedPreferencesValue(PREF_REGISTERED_USER, jsonUser)
    }

    companion object {
        private var Instance: UserSharedPreferences? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UserSharedPreferences()
        }
    }
}