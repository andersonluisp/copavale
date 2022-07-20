package com.andersonpimentel.faceitdata.login.data.datasource

import com.andersonpimentel.faceitdata.login.data.api.LoginService

class LoginRemoteDataSource (private val service: LoginService) {

    suspend fun getUserTokens(code: String) = service.getUserToken(code = code)
}