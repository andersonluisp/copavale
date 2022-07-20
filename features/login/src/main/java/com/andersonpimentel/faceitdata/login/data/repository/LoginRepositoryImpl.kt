package com.andersonpimentel.faceitdata.login.data.repository

import android.util.Log
import com.andersonpimentel.faceitdata.login.data.datasource.LoginLocalDataSource
import com.andersonpimentel.faceitdata.login.data.datasource.LoginRemoteDataSource
import com.andersonpimentel.faceitdata.login.data.mappers.toDomain
import com.andersonpimentel.faceitdata.login.data.mappers.toLocalData
import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.domain.model.UserToken
import com.andersonpimentel.faceitdata.login.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {

    override suspend fun getUserData(code: String): Flow<UserToken> {
        return flow {
            loginLocalDataSource.getUserTokens().collect { userTokens ->
                userTokens?.let {
                    emit(it.toDomain())
                } ?: getRemoteUserData(code).collect { userData ->
                    emit(userData.toDomain())
                }
            }
        }.flowOn(dispatcher)
    }

    override suspend fun verifyUserIsLogged(): Flow<Boolean> =
        flow {
            delay(2000)
            loginLocalDataSource.getUserTokens().collect { userTokens ->
                emit(userTokens != null)
            }
        }

    private suspend fun getRemoteUserData(code: String): Flow<UserTokenResponse> {
        return flow {
            val response =
                loginRemoteDataSource.getUserTokens(code)
            if (response.isSuccessful) {
                response.body()?.let { userData ->
                    saveUserData(userData)
                    emit(userData)
                }
            } else {
                Log.d("teste", response.errorBody().toString())
            }
        }.flowOn(dispatcher)
    }

    private fun saveUserData(userData: UserTokenResponse) {
        loginLocalDataSource.saveUserTokens(userData.toLocalData())
    }
}