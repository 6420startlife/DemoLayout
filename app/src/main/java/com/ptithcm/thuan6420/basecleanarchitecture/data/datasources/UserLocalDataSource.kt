package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import android.content.Context
import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.MySharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage.PREF_REGISTERED_USER

class UserLocalDataSource () {
    private lateinit var userSharedPreferences: MySharedPreferences
    fun init (context: Context) {
        userSharedPreferences = MySharedPreferences(context)
    }

    fun getUserFromLocal() : User {
        val jsonUser = userSharedPreferences.getSharedPreferencesValue(PREF_REGISTERED_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User) {
        val jsonUser = Gson().toJson(user)
        userSharedPreferences.putSharedPreferencesValue(PREF_REGISTERED_USER, jsonUser)
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