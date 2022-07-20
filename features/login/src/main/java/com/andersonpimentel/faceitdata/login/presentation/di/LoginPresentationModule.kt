package com.andersonpimentel.faceitdata.login.presentation.di

import com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginPresentationModule = module {
    viewModel {
        LoginViewModel(
            getUserTokenAccessUseCase = get(),
            verifyUserIsLoggedUseCase = get()
        )
    }
}