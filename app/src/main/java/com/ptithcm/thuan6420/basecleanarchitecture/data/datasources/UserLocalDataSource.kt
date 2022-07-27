package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import android.content.Context
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences
import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User

class UserLocalDataSource {
    private var userSharedPreferences: UserSharedPreferences? = null

    companion object {
        private const val PREF_REGISTERED_USER = "PREF_REGISTERED_USER"
        private var Instance: UserLocalDataSource? = null
        fun init(context: Context) {
            Instance = UserLocalDataSource()
            Instance?.userSharedPreferences = UserSharedPreferences(context)
        }

        fun getInstance(): UserLocalDataSource? {
            return if (Instance != null) {
                Instance
            } else UserLocalDataSource()
        }

        var user: User?
            get() {
                val jsonUser =
                    getInstance()!!.userSharedPreferences!!.getUserValue(
                        PREF_REGISTERED_USER
                    )
                val gson = Gson()
                return gson.fromJson(jsonUser, User::class.java)
            }
            set(user) {
                val gson = Gson()
                val jsonUser = gson.toJson(user)
                getInstance()!!.userSharedPreferences!!.putUserValue(PREF_REGISTERED_USER, jsonUser)
            }
    }
}