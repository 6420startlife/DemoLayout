package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.model.User
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.ConstantMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class UserViewModel(private val repository: UserRepository) : ViewModel(), Observable {
    @Bindable
    val inputEmail = MutableStateFlow("")

    @Bindable
    val errorEmail = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableStateFlow("")

    @Bindable
    val errorPassword = MutableLiveData<String?>()

    @Bindable
    val inputFullName = MutableStateFlow("")

    @Bindable
    val errorFullName = MutableLiveData<String?>()

    @Bindable
    val inputPhoneNumber = MutableStateFlow("")

    @Bindable
    val errorPhoneNumber = MutableLiveData<String?>()

    @Bindable
    val isUpdating = MutableStateFlow(false)
    val isSuccess = MutableLiveData(false)
    val message = MutableStateFlow("")

    private var _formLoginIsValid = false
    private var _formRegisterIsValid = false

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 9
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return Pattern.compile(ConstantMessage.REGEX_PHONE_NUMBER)
            .matcher(phoneNumber).matches()
    }

    private fun isStatusSuccess(status: Int): Boolean {
        return status == 200
    }

    val formLoginIsValid = combine(inputEmail, inputPassword) { inputEmail, inputPassword ->
        val emailIsEmpty = inputEmail.isEmpty()
        val emailIsValid = isValidEmail(inputEmail)
        val passwordIsEmpty = inputEmail.isEmpty()
        val passwordIsValid = isValidPassword(inputPassword)
        errorEmail.value = when {
            emailIsEmpty -> ConstantMessage.MESSAGE_EMPTY_EMAIL
            emailIsValid.not() -> ConstantMessage.MESSAGE_INVALID_EMAIL
            else -> null
        }
        errorPassword.value = when {
            passwordIsEmpty -> ConstantMessage.MESSAGE_EMPTY_EMAIL
            passwordIsValid.not() -> ConstantMessage.MESSAGE_INVALID_EMAIL
            else -> null
        }
        emailIsEmpty.not() and emailIsValid and
                passwordIsEmpty.not() and passwordIsValid
    }

    fun initFormLoginValid() = viewModelScope.launch {
        formLoginIsValid.collect{
            _formLoginIsValid = it
        }
    }

    fun initFormRegisterValid() = viewModelScope.launch {
        formRegisterIsValid.collect{
            _formRegisterIsValid = it
        }
    }

    val formRegisterIsValid = combine(inputEmail, inputPassword, inputFullName, inputPhoneNumber) {
        inputEmail, inputPassword, inputFullName, inputPhoneNumber ->
        val emailIsEmpty = inputEmail.isEmpty()
        val emailIsValid = isValidEmail(inputEmail)
        val passwordIsEmpty = inputEmail.isEmpty()
        val passwordIsValid = isValidPassword(inputPassword)
        val fullNameIsEmpty = inputFullName.isEmpty()
        val phoneNumberIsEmpty = inputPhoneNumber.isEmpty()
        val phoneNumberIsValid = isValidPhoneNumber(inputPhoneNumber)
        errorEmail.value = when {
            emailIsEmpty -> ConstantMessage.MESSAGE_EMPTY_EMAIL
            emailIsValid.not() -> ConstantMessage.MESSAGE_INVALID_EMAIL
            else -> null
        }
        errorPassword.value = when {
            passwordIsEmpty -> ConstantMessage.MESSAGE_EMPTY_EMAIL
            passwordIsValid.not() -> ConstantMessage.MESSAGE_INVALID_EMAIL
            else -> null
        }
        errorFullName.value = when {
            fullNameIsEmpty -> ConstantMessage.MESSAGE_EMPTY_FULL_NAME
            else -> null
        }
        errorPhoneNumber.value = when {
            phoneNumberIsEmpty -> ConstantMessage.MESSAGE_EMPTY_PHONE_NUMBER
            phoneNumberIsValid.not() -> ConstantMessage.MESSAGE_INVALID_PHONE_NUMBER
            else -> null
        }
        emailIsValid and passwordIsValid and phoneNumberIsValid
    }

    fun register() = viewModelScope.launch {
        if (_formRegisterIsValid) {
            Log.e("T64","Đã vào khi invalid value input")
            isUpdating.emit(true)
            Log.e("T64","Đã bật loading")
            try {
                val response = repository.createUserNetwork(
                        inputEmail.value, inputPassword.value,
                        inputFullName.value, inputPhoneNumber.value.toLong())
                Log.e("T64","Đã call api")
                isSuccess.postValue(isStatusSuccess(response!!.status))
                if (isSuccess.value == true) {
                    val user = User(
                        inputEmail.value, inputPassword.value,
                        inputFullName.value, inputPhoneNumber.value
                    )
                    repository.createUserInRoom(user)
                    message.emit(ConstantMessage.MESSAGE_SUCCESS_REGISTER)
                } else {
                    message.emit(response.message)
                }
            } catch (e: Exception) {
                isSuccess.postValue(false)
                message.emit(e.message.toString())
                Log.e("T64","Đã lỗi")
            } finally {
                isUpdating.emit(false)
                Log.e("T64","Đã tắt loading")
            }
        } else {
            isSuccess.postValue(false)
            message.emit(ConstantMessage.MESSAGE_ERROR_VALID)
        }
    }

    fun login() = viewModelScope.launch {
        if (_formLoginIsValid) {
            isUpdating.emit(true)
            try {
                val result = repository.checkUserNetwork(inputEmail.value, inputPassword.value)
                if (result) {
                    message.emit(ConstantMessage.MESSAGE_SUCCESS_LOGIN)
                } else {
                    message.emit(ConstantMessage.MESSAGE_ERROR_LOGIN)
                }
                isSuccess.postValue(result)
            } catch (e : Exception) {
                message.emit(e.message.toString())
                isSuccess.postValue(false)
            } finally {
                isUpdating.emit(false)
            }
        } else {
            isSuccess.postValue(false)
            message.emit(ConstantMessage.MESSAGE_ERROR_VALID)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}