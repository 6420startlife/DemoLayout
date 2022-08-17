package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(private val apiHelper: ApiHelper, private val dao: UserDao) {

    val latestUsers: Flow<List<User>> = flow {
        val latestUsers = dao.getAll()
        emit(latestUsers)
    }

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

    fun createUserBySharedPreferences(user: User) {
        UserSharedPreferences().setUserFromLocal(user)
    }

    fun isMatchedUserSharedPreferences(user: User): Boolean {
        val (id, email, password) = UserSharedPreferences().getUserFromLocal()
        return (user.email == email
                && user.password == password)
    }

    fun isExistedUserSharedPreferences(user: User): Boolean {
        val (id, email) = UserSharedPreferences().getUserFromLocal()
        return user.email == email
    }

    suspend fun register(
        email: String, password: String,
        fullName: String, phoneNumber: Number
    ) = apiHelper.register(email, password, fullName, phoneNumber)

    suspend fun login(email: String, password: String) = apiHelper.login(email, password)
}