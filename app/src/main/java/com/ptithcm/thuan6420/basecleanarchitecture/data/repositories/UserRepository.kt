package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.LoginNetworkDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ResponseRegister
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User

class UserRepository {
    fun createUser(user: User?) {
        UserLocalDataSource.user = user
    }

    fun isMatchedUser(user: User): Boolean {
        val (email, password) = UserLocalDataSource.user ?: return false
        return (user.email.equals(email, ignoreCase = true)
                && user.password.equals(password, ignoreCase = true))
    }

    fun isExistedUser(user: User): Boolean {
        val (email) = UserLocalDataSource.user ?: return false
        return user.email.equals(email, ignoreCase = true)
    }

    suspend fun createUserNetwork(
        email: String, password: String,
        fullName: String, phoneNumber: Number
    ): ResponseRegister? {
        val response =
            LoginNetworkDataSource().loginApi.register(email, password, fullName, phoneNumber)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun checkUserNetwork(email: String, password: String): Boolean {
        val response = LoginNetworkDataSource().loginApi.login(email, password)
        return if (response.isSuccessful) {
            response.body()?.status == 200
        } else {
            false
        }
    }


    companion object {
        private var Instance: UserRepository? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UserRepository()
        }
    }
}