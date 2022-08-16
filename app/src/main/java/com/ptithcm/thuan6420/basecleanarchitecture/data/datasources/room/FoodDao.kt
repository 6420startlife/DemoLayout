package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room

import androidx.room.*
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import kotlinx.coroutines.flow.Flow

interface FoodDao {
    @Query("SELECT * FROM product_table")
    suspend fun getAll() : Flow<List<User>>

    @Query("SELECT * FROM product_table WHERE id_product LIKE (:email)")
    suspend fun findByEmail(email : String) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAnUser(user: User) : Long

    @Update
    suspend fun updateAnUser(user: User)

    @Delete
    suspend fun deleteAnUser(user: User)
}