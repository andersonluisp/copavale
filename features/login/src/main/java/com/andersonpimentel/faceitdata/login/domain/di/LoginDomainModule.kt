package com.andersonpimentel.faceitdata.login.domain.di

import com.andersonpimentel.faceitdata.login.domain.usecase.GetUserTokenAccessUseCase
import com.andersonpimentel.faceitdata.login.domain.usecase.SaveLoginCodeUseCase
import org.koin.dsl.module

val loginDomainModule = module {

    factory { GetUserTokenAccessUseCase(loginRepository = get()) }

    factory { SaveLoginCodeUseCase(loginRepository = get()) }

}