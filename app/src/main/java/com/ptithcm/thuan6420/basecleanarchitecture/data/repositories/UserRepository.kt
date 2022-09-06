package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiHelper: ApiHelper,
                                         private val dao: UserDao,
                                         private val appSharedPreferences: AppSharedPreferences) {
    fun getUserFromLocal(): User {
        val jsonUser = appSharedPreferences.getSharedPreferencesValue(Constants.PREF_REGISTERED_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User) {
        val jsonUser = Gson().toJson(user)
        appSharedPreferences.putSharedPreferencesValue(Constants.PREF_REGISTERED_USER, jsonUser)
    }

    suspend fun getAllUsers(): List<User> = dao.getAll()

    suspend fun findByEmail(email: String) = dao.findByEmail(email)

    suspend fun create(user: User) {
        dao.createAnUser(user)
    }

    suspend fun update(user: User) {
        dao.updateAnUser(user)
    }

    suspend fun delete(user: User) {
        dao.deleteAnUser(user)
    }

    suspend fun login(email: String, password: String) = apiHelper.login(email, password)

    suspend fun register(email: String, password: String, fullName: String, phoneNumber: Number)
    = apiHelper.register(email, password, fullName, phoneNumber)
}