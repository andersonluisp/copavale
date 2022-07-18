package com.andersonpimentel.faceitdata.login.data.datasource

import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse

object LoginDataStub {
    var loginCode: String? = null
    private var userTokenResponse: UserTokenResponse? = null

    fun saveLoginCode(code: String) {
        loginCode = code
    }

    fun saveUserData(userData: UserTokenResponse) {
        userTokenResponse = userData
    }

    fun getUserData(): UserTokenResponse? = userTokenResponse
}