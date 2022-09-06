package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.DEFAULT_ERROR_MESSAGE
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import com.ptithcm.thuan6420.basecleanarchitecture.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository,
    private val network: Network,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    fun fetchFood() = liveData(coroutineContext) {
        try {
            emit(Resource.loading(data = null))
            if (network.isConnected().not()) {
                val localData = foodRepository.getFoodFromLocal()
                emit(Resource.success(data = localData, message = null))
            } else {
                val response = userRepository.getUserFromLocal().id?.let {
                    foodRepository.fetchData(
                        it
                    )
                }
                response?.apply {
                    if (isSuccessful) {
                        if (body()?.status == 200) {
                            val responseFood = body()?.data
                            emit(Resource.success(data = responseFood, message = null))
                        } else {
                            emit(Resource.error(data = null, message = body()?.message))
                        }
                    } else {
                        emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
                    }
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
            emit(Resource.error(data = null, message = DEFAULT_ERROR_MESSAGE))
        }
    }

    companion object{
        const val TAG = "FoodViewModel"
    }
}