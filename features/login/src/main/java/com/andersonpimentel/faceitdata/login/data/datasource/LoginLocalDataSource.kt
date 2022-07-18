package com.andersonpimentel.faceitdata.login.data.datasource

import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse

class LoginLocalDataSource (
    private val loginDB: LoginDataStub
        ) {

    suspend fun saveCodeLogin(code: String) {
        loginDB.saveLoginCode(code)
    }

    suspend fun saveUserData(user: UserTokenResponse) {
        loginDB.saveUserData(user)
    }

    suspend fun getUserData() : UserTokenResponse? = loginDB.getUserData()

    suspend fun getLoginCode() : String? = loginDB.loginCode
}