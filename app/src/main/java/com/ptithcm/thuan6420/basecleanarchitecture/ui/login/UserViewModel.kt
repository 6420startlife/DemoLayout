package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository,
                                        private val coroutineContext: CoroutineContext) : ViewModel() {

    fun register(email: String,
                 password: String,
                 fullName: String,
                 phoneNumber: Number
    ) = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            val response = userRepository.register(email, password, fullName, phoneNumber)
            response.apply {
                if (isSuccessful) {
                    if (body()?.status == 200) {
                        emit(Resource.success(data = null, message = body()?.message))
                    } else {
                        emit(Resource.error(data = null, message = body()?.message))
                    }
                } else {
                    emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
            Log.e("T64", exception.message.toString())
        }
    }

    fun login(email: String, password: String)
            = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            val response = userRepository.login(email, password)
            response.apply {
                if (isSuccessful) {
                    if (body()?.status == 200) {
                        val responseUser = body()?.data as User
                        userRepository.create(user = responseUser)
                        userRepository.setUserFromLocal(user = responseUser)
                        emit(Resource.success(data = null, message = body()?.message))
                    } else {
                        emit(Resource.error(data = null, message = body()?.message))
                    }
                } else {
                    emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
        }
    }

    companion object{
        const val TAG = "UserViewModel"
    }
}