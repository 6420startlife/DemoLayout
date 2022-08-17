package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.util.Log
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.sharepreferences.FoodSharedPreferences
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.util.Network.isConnected

class HomePresenter(private val repository: FoodRepository,
                    private val view: HomeContact.ViewInterface) :
    HomeContact.PresenterInterface {

    override suspend fun onFetchingData(userID: Int) {
        view.showLoading()
        try {
            if (isConnected()) {
                val response = repository.fetchFood(userID)
                if (response.isSuccessful) {
                    view.fetchData(response.body())
                    response.body()?.let {
                        FoodSharedPreferences().setFoodFromLocal(it)
                    }
                } else {
                    view.showError("Loading failed")
                }
            } else {
                view.fetchData(FoodSharedPreferences().getFoodFromLocal())
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            view.showError("Error Occurred!")
        } finally {
            view.hideLoading()
        }
    }
}