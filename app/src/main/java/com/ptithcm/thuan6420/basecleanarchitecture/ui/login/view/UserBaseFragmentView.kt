package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDbDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.room.MyDatabase
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository
import com.ptithcm.thuan6420.basecleanarchitecture.ui.dialogs.DialogListener
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.viewmodel.UserViewModel
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.viewmodel.UserViewModelFactory
import com.ptithcm.thuan6420.basecleanarchitecture.ui.utility.baseview.BaseFragmentView
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class UserBaseFragmentView : BaseFragmentView(), DialogListener, setOnLoadingListener {
    abstract val userViewModel: UserViewModel

    fun initViewModel(): UserViewModel {
        val userDao = MyDatabase.getInstance(this.requireActivity().application).userDao
        val dataSource = UserLocalDbDataSource(userDao)
        val repository = UserRepository(dataSource)
        val factory = UserViewModelFactory(repository)
        return ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFormValid()
        setValidate()
        setLoading(this)
        setDialog()
    }

    abstract fun setFormValid(): Job

    private fun setDialog() {
        userViewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                onSuccess(userViewModel.message.value, this)
            } else if (userViewModel.message.value.isNotEmpty()) {
                onFailure(userViewModel.message.value, null)
            }
        }
    }

    abstract fun setValidate()

    abstract fun doWhenShowSuccessDialog()

    private fun setLoading(listener: setOnLoadingListener) = lifecycleScope.launch {
        userViewModel.isUpdating.collect {
            if (it) {
                turnOnLoading(listener)
            } else {
                turnOffLoading(listener)
            }
        }
    }

    override fun onClickButtonOK() {
        doWhenShowSuccessDialog()
    }
}