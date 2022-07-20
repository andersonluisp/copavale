package com.andersonpimentel.faceitdata.login.data.model

import com.google.gson.annotations.SerializedName

data class UserTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)