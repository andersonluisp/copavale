package com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepositoryImpl
): ViewModel() {

    private val _userDataLiveData = MutableLiveData<UserTokenResponse>()
    val userDataLiveData: LiveData<UserTokenResponse> = _userDataLiveData

    private fun saveLoginCode(code: String) {
        loginRepository.saveLoginCode(code)
    }

    private fun getUserData() {
        viewModelScope.launch {
            loginRepository.getUserData().collect { userData ->
                _userDataLiveData.value = userData
            }
        }
    }

    fun receiveLoginCode(code: String?) {
        code?.let {
            saveLoginCode(it)
            getUserData()
        }
    }
}