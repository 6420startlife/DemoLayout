package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserLocalDbDataSource(private val dao: UserDao) {
    val latestUser : Flow<List<User>> = flow {
        val latestUser = dao.getAll()
        emit(latestUser)
    }

    suspend fun findByEmail(email : String) = dao.findByEmail(email)

    suspend fun create(user: User) {
        dao.createAnUser(user)
    }

    suspend fun update(user: User) {
        dao.updateAnUser(user)
    }

    suspend fun delete(user: User) {
        dao.deleteAnUser(user)
    }
}