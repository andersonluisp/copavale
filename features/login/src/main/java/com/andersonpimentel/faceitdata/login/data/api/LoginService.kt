package com.andersonpimentel.faceitdata.login.data.api

import com.andersonpimentel.faceitdata.login.data.model.UserTokenResponse
import com.andersonpimentel.faceitdata.login.util.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST(Constants.TOKEN_ENDPOINT)
    @Headers(Constants.AUTHORIZATION)
    suspend fun getUserToken(
        @Field("code") code: String,
        @Field("grant_type") grantType: String = "authorization_code",
    ) : Response<UserTokenResponse>
}