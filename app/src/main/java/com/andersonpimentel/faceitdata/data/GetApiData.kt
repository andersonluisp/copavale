package com.andersonpimentel.faceitdata.data

import com.andersonpimentel.faceitdata.data.models.championship.ChampionshipsList
import com.andersonpimentel.faceitdata.data.models.matches.MatchesList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface GetApiData {

    @Headers("Authorization: Bearer 010e1835-fd08-4e5d-8e56-98f33b79d83f")
    @GET("https://open.faceit.com/data/v4/organizers/{id}/championships")
    suspend fun getChampionships(
        @Path("id",)id: String, @Query("offset")offset: String, @Query("limit")limit: String): ChampionshipsList

    @Headers("Authorization: Bearer 010e1835-fd08-4e5d-8e56-98f33b79d83f")
    @GET("https://open.faceit.com/data/v4/championships/{id}/matches")
    suspend fun getMatches(
        @Path("id",)id: String, @Query("offset")offset: String, @Query("limit")limit: String): MatchesList

    companion object{

        var getApiService: GetApiData? = null
       // var teste: Retrofit? = null

        fun getInstance() : GetApiData {

            val okhttp = OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .build()

            if (getApiService ==null) {
                val retrofit = Retrofit.Builder()
                    .client(okhttp)
                    .baseUrl(ApiClientInstance.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                getApiService = retrofit.create(GetApiData::class.java)
            }
            return getApiService!!
        }
    }

}