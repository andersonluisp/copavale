package com.andersonpimentel.faceitdata.login.data.mappers

import com.andersonpimentel.faceitdata.login.data.model.UserTokenLocalModel
import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.domain.model.UserToken

fun UserTokenResponse.toLocalData() =  UserTokenLocalModel(
    accessToken = accessToken,
    expiresIn = expiresIn,
    idToken = idToken,
    refreshToken = refreshToken,
    scope = scope,
    tokenType = tokenType
)

fun UserTokenResponse.toDomain() = UserToken(
    accessToken = accessToken,
    expiresIn = expiresIn,
    idToken = idToken,
    refreshToken = refreshToken,
    scope = scope,
    tokenType = tokenType
)