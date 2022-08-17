package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.dto.home.ResponseFood

interface HomeContact {
    interface PresenterInterface{
        suspend fun onFetchingData(userID: Int)
    }
    interface ViewInterface{
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun fetchData(data: ResponseFood?)
    }
}