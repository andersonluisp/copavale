package com.andersonpimentel.faceitdata.login.domain.usecase

import com.andersonpimentel.faceitdata.login.domain.repository.LoginRepository

class VerifyUserIsLoggedUseCase(private val loginRepository: LoginRepository) {
    suspend operator fun invoke() =
        loginRepository.verifyUserIsLogged()
}