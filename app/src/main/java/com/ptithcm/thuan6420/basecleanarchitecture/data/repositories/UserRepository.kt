package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.LoginNetworkDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.LoginApi
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ResponseLogin
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ResponseRegister
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import kotlinx.coroutines.*
import retrofit2.*

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

    suspend fun createUserNetwork(email : String, password : String,
                                  fullName : String, phoneNumber : Number) : Boolean {
        val response = LoginNetworkDataSource().loginApi.register(email, password, fullName, phoneNumber)
        return if (response.isSuccessful) {
            response.body()?.status == 200
        } else {
            false
        }
    }

    suspend fun checkUserNetwork(email : String, password : String) : Boolean {
        val response = LoginNetworkDataSource().loginApi.login(email, password)
        return if (response.isSuccessful) {
            response.body()?.status == 200
        } else {
            false
        }
    }



    companion object{
        private var Instance : UserRepository? = null
        operator fun invoke() = synchronized(this) {
            if (Instance != null) {
                Instance
            }
            Instance = UserRepository()
        }
    }
}