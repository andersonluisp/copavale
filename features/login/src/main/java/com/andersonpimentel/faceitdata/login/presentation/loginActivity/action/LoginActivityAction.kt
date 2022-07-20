package com.andersonpimentel.faceitdata.login.presentation.loginActivity.action

import com.andersonpimentel.faceitdata.baseviewmodel.action.ViewAction
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel.LoginViewModel

sealed class LoginActivityAction : ViewAction {
    object NavigateToMainActivity : LoginActivityAction()
    object OpenLoginPage : LoginActivityAction()
    object ReceiveCodeLogin : LoginActivityAction()
}