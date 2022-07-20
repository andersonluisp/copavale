package com.andersonpimentel.faceitdata.login.domain.repository

import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.domain.model.UserToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getUserData(code: String): Flow<UserToken>

    suspend fun verifyUserIsLogged(): Flow<Boolean>

}