package com.andersonpimentel.faceitdata.login.domain.usecase

import com.andersonpimentel.faceitdata.login.domain.repository.LoginRepository

class SaveLoginCodeUseCase(private val loginRepository: LoginRepository) {
    operator fun invoke(code: String) = loginRepository.saveLoginCode(code)
}