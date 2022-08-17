package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.IO_DISPATCHER
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.MESSAGE_ERROR_LOADING
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.FoodSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.UserSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network.isConnected
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource

class FoodViewModel(private val repository: FoodRepository): ViewModel() {

    fun fetchData() = liveData(IO_DISPATCHER){
        emit(Resource.loading(data = null))
        try {
            if (isConnected()) {
                val response = repository.fetchFood(UserSharedPreferences().getUserFromLocal().id)
                if (response.isSuccessful) {
                    emit(Resource.success(data = response.body(), message = null))
                    response.body()?.let {
                        FoodSharedPreferences().setFoodFromLocal(it)
                    }
                } else {
                    emit(Resource.failed(data = null, message = MESSAGE_ERROR_LOADING))
                }
            } else {
                emit(Resource.success(data = FoodSharedPreferences().getFoodFromLocal(), message = null))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
            Log.e("T64", exception.message.toString())
        }
    }
}