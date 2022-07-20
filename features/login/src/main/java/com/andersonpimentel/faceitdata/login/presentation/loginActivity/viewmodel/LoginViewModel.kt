package com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.andersonpimentel.faceitdata.baseviewmodel.viewmodel.BaseViewModel
import com.andersonpimentel.faceitdata.login.domain.usecase.GetUserTokenAccessUseCase
import com.andersonpimentel.faceitdata.login.domain.usecase.VerifyUserIsLoggedUseCase
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.action.LoginActivityAction
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.state.LoginActivityState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoginViewModel(
    private val getUserTokenAccessUseCase: GetUserTokenAccessUseCase,
    private val verifyUserIsLoggedUseCase: VerifyUserIsLoggedUseCase
) : BaseViewModel<LoginActivityState, LoginActivityAction>(LoginActivityState()) {

    init {
        verifyUserIsLogged()
    }

    private fun getUserData(code: String) {
        handleLoadingState()
        viewModelScope.launch {
            runCatching { getUserTokenAccessUseCase(code) }
                .onSuccess {
                    it.collect {
                        onUserTokensReceived()
                    }
                }
                .onFailure {
                    Log.e("teste", it.stackTraceToString())
                }
        }
    }

    fun receiveLoginCode(code: String?) {
        code?.let {
            getUserData(code)
        } ?: showContentState()
    }

    private fun verifyUserIsLogged() {
        viewModelScope.launch {
            verifyUserIsLoggedUseCase().collect { isLogged ->
                if (isLogged) {
                    sendAction {
                        LoginActivityAction.NavigateToMainActivity
                    }
                } else {
                    sendAction {
                        LoginActivityAction.ReceiveCodeLogin
                    }
                }
            }
        }
    }

    fun onLoginButtonClicked() {
        handleLoadingState()
        sendAction {
            LoginActivityAction.OpenLoginPage
        }
    }

    fun closedLoginScreen() {
        showContentState()
    }

    private fun onUserTokensReceived() {
        sendAction {
            LoginActivityAction.NavigateToMainActivity
        }
    }

    private fun handleLoadingState() {
        setState { state ->
            state.setLoadingState()
        }
    }

    private fun showContentState() {
        setState { state ->
            state.setShowContentState()
        }
    }
}