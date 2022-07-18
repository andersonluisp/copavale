package com.andersonpimentel.faceitdata.login.domain.model

data class UserToken(
    val accessToken: String,
    val expiresIn: Int,
    val idToken: String,
    val refreshToken: String,
    val scope: String,
    val tokenType: String
)