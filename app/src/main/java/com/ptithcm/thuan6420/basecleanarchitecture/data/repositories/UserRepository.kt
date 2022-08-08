package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.LoginNetworkDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDbDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ResponseRegister
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UserRepository(private val localDataSource: UserLocalDbDataSource) {
    private val ioDispatchers = Dispatchers.IO
    val user = localDataSource.latestUser

    suspend fun createUserInRoom(user: User) = withContext(ioDispatchers) {
        localDataSource.create(user)
    }

    suspend fun checkUserInRoom(user: User): Boolean = withContext(ioDispatchers) {
        val userInRoom = localDataSource.findByEmail(user.email)
        return@withContext userInRoom.password == user.password
    }

    suspend fun updateUserInRoom(user: User) = withContext(ioDispatchers) {
        localDataSource.update(user)
    }

    suspend fun deleteUserInRoom(user: User) = withContext(ioDispatchers) {
        localDataSource.delete(user)
    }

    suspend fun findUserFromRoom(email: String) = withContext(ioDispatchers) {
        localDataSource.findByEmail(email)
    }

    fun createUserBySharedPreferences(user: User) {
        UserLocalDataSource().setUserFromLocal(user)
    }

    fun isMatchedUserSharedPreferences(user: User): Boolean {
        val (email, password) = UserLocalDataSource().getUserFromLocal()
        return (user.email == email
                && user.password == password)
    }

    fun isExistedUserSharedPreferences(user: User): Boolean {
        val (email) = UserLocalDataSource().getUserFromLocal()
        return user.email == email
    }

    suspend fun createUserNetwork(
        email: String, password: String,
        fullName: String, phoneNumber: Number
    ): ResponseRegister? = withContext(ioDispatchers) {
        val response =
            LoginNetworkDataSource().loginApi.register(email, password, fullName, phoneNumber)
        delay(6000)
        return@withContext if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun checkUserNetwork(email: String, password: String): Boolean =
        withContext(ioDispatchers) {
            val response = LoginNetworkDataSource().loginApi.login(email, password)
            delay(6000)
            return@withContext if (response.isSuccessful) {
                response.body()?.status == 200
            } else {
                false
            }
        }
}