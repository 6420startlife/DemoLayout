package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ptithcm.thuan6420.basecleanarchitecture.Constants
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_SUCCESS_LOGIN
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_SUCCESS_REGISTER
import com.ptithcm.thuan6420.basecleanarchitecture.data.network.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.persistence.UserDao
import com.ptithcm.thuan6420.basecleanarchitecture.data.sharepreferences.AppSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.User
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepository @Inject constructor(private val apiHelper: ApiHelper,
                                         private val dao: UserDao,
                                         private val appSharedPreferences: AppSharedPreferences,
                                         private val coroutineContext: CoroutineContext) {
    fun getUserFromLocal(): User {
        val jsonUser = appSharedPreferences.getSharedPreferencesValue(Constants.PREF_REGISTERED_USER)
        return Gson().fromJson(jsonUser, User::class.java)
    }

    fun setUserFromLocal(user: User) {
        val jsonUser = Gson().toJson(user)
        appSharedPreferences.putSharedPreferencesValue(Constants.PREF_REGISTERED_USER, jsonUser)
    }

    suspend fun getAllUsers(): List<User> = dao.getAll()

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

    fun register(email: String,
                 password: String,
                 fullName: String,
                 phoneNumber: Number,
                 onComplete: (CoroutineContext) -> Unit
    ) = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            delay(500L)
            val response = apiHelper.register(email, password, fullName, phoneNumber)
            response.apply {
                if (isSuccessful.not()) {
                    emit(Resource.failed(data = false, message = message()))
                }
                if (body()?.status == 200) {
                    onComplete(coroutineContext)
                    emit(Resource.success(data = true, message = MESSAGE_SUCCESS_REGISTER))

                } else {
                    emit(Resource.failed(data = false, message = body()?.message))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
            Log.e("T64", exception.message.toString())
        }
    }

    fun login(email: String, password: String, onComplete: (User) -> Unit)
    = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            delay(500L)
            val response = apiHelper.login(email, password)
            response.apply {
                if (isSuccessful.not()) {
                    emit(Resource.failed(data = false, message = message()))
                }
                if (body()?.status == 200) {
                    val responseUser = body()?.data
                    onComplete(User(responseUser?.id,
                        responseUser?.email.toString(),
                        null,
                        responseUser?.name,
                        responseUser?.phone_number))
                    emit(Resource.success(data = true, message = MESSAGE_SUCCESS_LOGIN))
                } else {
                    emit(Resource.failed(data = false, message = body()?.message))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
            Log.e("T64", exception.message.toString())
        }
    }
}