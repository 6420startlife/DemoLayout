package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.MESSAGE_SUCCESS_LOGIN
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.MESSAGE_SUCCESS_REGISTER
import com.ptithcm.thuan6420.basecleanarchitecture.util.Constants.ioDispatcher
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, password: String) =
        liveData(ioDispatcher) {
            emit(Resource.loading(data = null))
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    if (response.body()?.status == 200) {
                        val user = User(response.body()!!.data.id, response.body()!!.data.email,"",
                            response.body()!!.data.name, response.body()!!.data.phone_number)
                        UserLocalDataSource().setUserFromLocal(user)
                        emit(Resource.success(data = response, message = MESSAGE_SUCCESS_LOGIN))
                    } else {
                        emit(Resource.failed(data = null, message = response.body()?.message))
                    }
                } else {
                    emit(Resource.failed(data = null, message = response.message()))
                }
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: DEFAULT_ERROR_MESSAGE))
                Log.e("T64",exception.message.toString())
            }
        }

    fun register(email: String, password: String, fullName: String, phoneNumber: Number) =
        liveData(ioDispatcher) {
            emit(Resource.loading(data = null))
            try {
                val response = repository.register(email, password, fullName, phoneNumber)
                if (response.isSuccessful) {
                    if (response.body()?.status == 200) {
                        emit(Resource.success(data = response, message = MESSAGE_SUCCESS_REGISTER))
                    } else {
                        emit(Resource.failed(data = null, message = response.body()?.message))
                    }
                } else {
                    emit(Resource.failed(data = null, message = response.message()))
                }
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: DEFAULT_ERROR_MESSAGE))
            }
        }
}