package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import androidx.lifecycle.ViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val userRepository: UserRepository,
    private val network: Network
) : ViewModel() {

    fun fetchData() = repository.fetchFood(userId = userRepository.getUserFromLocal().id ?: 0,
        isConnected = { network.isConnected() },
        onComplete = { it?.let { data -> repository.setFoodFromLocal(data) } })
}