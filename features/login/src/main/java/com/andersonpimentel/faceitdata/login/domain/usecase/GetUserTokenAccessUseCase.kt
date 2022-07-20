package com.andersonpimentel.faceitdata.login.domain.usecase

import com.andersonpimentel.faceitdata.login.domain.model.UserToken
import com.andersonpimentel.faceitdata.login.domain.repository.LoginRepository

class GetUserTokenAccessUseCase(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(code: String) = loginRepository.getUserData(code)
}