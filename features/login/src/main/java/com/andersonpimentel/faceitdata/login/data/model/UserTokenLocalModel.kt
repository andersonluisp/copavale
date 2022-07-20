package com.andersonpimentel.faceitdata.login.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_tokens")
data class UserTokenLocalModel(
    @PrimaryKey
    val accessToken: String,
    val expiresIn: Int,
    val idToken: String,
    val refreshToken: String,
    val scope: String,
    val tokenType: String
)