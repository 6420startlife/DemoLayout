package com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room

import android.content.Context
import androidx.room.*
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User

@Database(entities = [User::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    companion object {
        @Volatile
        private var Instance : MyDatabase? = null
        fun getInstance(context: Context) : MyDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        MyDatabase::class.java,
                        "my_db")
                        .build()
                }
                return instance
            }
        }
    }
}