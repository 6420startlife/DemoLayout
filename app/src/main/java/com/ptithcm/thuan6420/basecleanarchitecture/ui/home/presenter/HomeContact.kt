package com.ptithcm.thuan6420.basecleanarchitecture.ui.home.presenter

class HomeContact {
    interface PresenterInterface{
        fun onLoadingData()
    }
    interface ViewInterface{
        fun onLoading(status : Boolean)
    }
}