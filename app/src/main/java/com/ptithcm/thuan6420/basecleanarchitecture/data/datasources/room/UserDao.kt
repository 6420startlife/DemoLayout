package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room

import androidx.room.*
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    suspend fun getAll() : List<User>

    @Query("SELECT * FROM user_table WHERE user_email LIKE (:email)")
    suspend fun findByEmail(email : String) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAnUser(user: User) : Long

    @Update
    suspend fun updateAnUser(user: User)

    @Delete
    suspend fun deleteAnUser(user: User)

}