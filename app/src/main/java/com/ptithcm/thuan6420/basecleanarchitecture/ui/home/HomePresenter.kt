package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.util.Log
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.FoodLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository

class HomePresenter(private val repository: FoodRepository,
                    private val view: HomeContact.ViewInterface) :
    HomeContact.PresenterInterface {

    override suspend fun onFetchingData(userID: Int) {
        view.showLoading()
        try {
            val response = repository.fetchFood(userID)
            if (response.isSuccessful) {
                view.fetchData(response.body())
                response.body()?.let {
                    FoodLocalDataSource().setFoodFromLocal(it)
                }
            } else {
                view.showError("Loading failed")
            }
        } catch (exception: Exception) {
            Log.e("T64", exception.message.toString())
            view.showError("Error Occurred!")
        } finally {
            view.hideLoading()
        }
    }
}