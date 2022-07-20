package com.andersonpimentel.faceitdata.login.data.datasource

import com.andersonpimentel.faceitdata.login.data.local.LoginDataBase
import com.andersonpimentel.faceitdata.login.data.model.UserTokenLocalModel
import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse

class LoginLocalDataSource(
    private val loginDB: LoginDataBase
) {
    fun saveUserTokens(user: UserTokenLocalModel) = loginDB.userDataDao().insertUserTokens(user)

    fun deleteUserToken() = loginDB.userDataDao().deleteUserToken()

    fun getUserTokens() = loginDB.userDataDao().getSavedUserTokens()
}