package com.ptithcm.thuan6420.basecleanarchitecture.util

import com.ptithcm.thuan6420.basecleanarchitecture.Constants

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T, message: String?): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = message)

        fun <T> error(data: T?, message: String?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message ?: Constants.DEFAULT_ERROR_MESSAGE)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}
