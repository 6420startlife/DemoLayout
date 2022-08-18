package com.ptithcm.thuan6420.basecleanarchitecture.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
}