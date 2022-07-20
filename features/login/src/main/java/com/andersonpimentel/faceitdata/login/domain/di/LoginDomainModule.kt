package com.andersonpimentel.faceitdata.login.domain.di

import com.andersonpimentel.faceitdata.login.domain.usecase.GetUserTokenAccessUseCase
import com.andersonpimentel.faceitdata.login.domain.usecase.VerifyUserIsLoggedUseCase
import org.koin.dsl.module

val loginDomainModule = module {

    factory { GetUserTokenAccessUseCase(loginRepository = get()) }

    factory { VerifyUserIsLoggedUseCase(loginRepository = get()) }

}