package com.andersonpimentel.faceitdata.login.domain.repository

import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun saveLoginCode(code: String)

    suspend fun getUserData(): Flow<UserTokenResponse>

}