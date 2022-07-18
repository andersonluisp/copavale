package com.andersonpimentel.faceitdata.login.data.repository

import android.util.Log
import com.andersonpimentel.faceitdata.login.data.datasource.LoginLocalDataSource
import com.andersonpimentel.faceitdata.login.data.datasource.LoginRemoteDataSource
import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): LoginRepository {

    override fun saveLoginCode(code: String) {
        CoroutineScope(dispatcher).launch {
            loginLocalDataSource.saveCodeLogin(code)
        }
    }

    override suspend fun getUserData(): Flow<UserTokenResponse> {
        return withContext(dispatcher){
            flow {
                if (loginLocalDataSource.getUserData() != null) {
                    val userData = loginLocalDataSource.getUserData()
                    userData?.let {
                        emit (it)
                    }
                } else {
                    getRemoteUserData().collect { userData ->
                        userData?.let {
                            emit(it)
                        }
                    }
                }
            }
        }
    }

    private suspend fun getRemoteUserData() : Flow<UserTokenResponse?> {
        return flow {
            val response = loginRemoteDataSource.getUserTokens(loginLocalDataSource.getLoginCode() ?: "")
            if (response.isSuccessful) {
                response.body()?.let { userData ->
                    emit(userData)
                    saveUserData(userData)
                }
            } else {
                Log.d("teste", response.errorBody().toString())
            }
        }
    }

    private suspend fun saveUserData(userData: UserTokenResponse) {
        loginLocalDataSource.saveUserData(userData)
    }
}