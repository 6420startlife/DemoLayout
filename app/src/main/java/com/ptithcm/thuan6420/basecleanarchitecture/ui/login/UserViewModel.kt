package com.ptithcm.thuan6420.basecleanarchitecture.ui.login

import androidx.lifecycle.ViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password, onComplete = {
        repository.setUserFromLocal(it)
    })

    fun register(email: String, password: String, fullName: String, phoneNumber: Number) =
        repository.register(email, password, fullName, phoneNumber, onComplete = {
            CoroutineScope(it).launch {
                repository.create(User(null, email, password, fullName, phoneNumber.toString()))
            }
        })
}