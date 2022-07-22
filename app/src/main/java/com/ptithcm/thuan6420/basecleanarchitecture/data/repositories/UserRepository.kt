package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.LoginNetworkDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ResponseLogin
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse

class UserRepository {
    fun createUser(user: User?) {
        UserLocalDataSource.setUser(user)
    }

    fun isMatchedUser(user: User): Boolean {
        val (email, password) = UserLocalDataSource.getUser() ?: return false
        return (user.email.equals(email, ignoreCase = true)
                && user.password.equals(password, ignoreCase = true))
    }

    fun isExistedUser(user: User): Boolean {
        val (email) = UserLocalDataSource.getUser() ?: return false
        return user.email.equals(email, ignoreCase = true)
    }

    suspend fun createUserNetwork(email : String, password : String,
                                  fullName : String, phoneNumber : Number): ResponseLogin?
      =  LoginNetworkDataSource().loginApi.register(email, password, fullName, phoneNumber)?.await()

    suspend fun checkUserNetwork(email : String, password : String): Call<ResponseLogin?>?
            =  LoginNetworkDataSource().loginApi.login(email, password)

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