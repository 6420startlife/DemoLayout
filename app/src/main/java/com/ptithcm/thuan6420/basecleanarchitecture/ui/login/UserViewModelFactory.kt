package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository

class UserViewModelFactory(private val apiHelper: ApiHelper, private val dao: UserDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(apiHelper, dao)) as T
        }
        throw IllegalAccessException("Unknown View Model Class")
    }
}