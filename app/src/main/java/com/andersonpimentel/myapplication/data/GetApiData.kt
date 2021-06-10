package com.andersonpimentel.myapplication.data

import com.andersonpimentel.myapplication.data.models.championship.ChampionshipsList
import com.andersonpimentel.myapplication.data.models.matches.MatchesList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GetApiData {

    @Headers("Authorization: Bearer 2f52d9e7-2f15-472e-9210-ce91616d214e")
    @GET("https://open.faceit.com/data/v4/organizers/{id}/championships")
    fun getChampionships(@Path("id",)id: String, @Query("offset")offset: String, @Query("limit")limit: String): Call<ChampionshipsList>

    @Headers("Authorization: Bearer 2f52d9e7-2f15-472e-9210-ce91616d214e")
    @GET("https://open.faceit.com/data/v4/championships/{id}/matches")
    fun getMatches(@Path("id",)id: String, @Query("offset")offset: String, @Query("limit")limit: String): Call<MatchesList>

    companion object{

        var getApiService: GetApiData? = null
       // var teste: Retrofit? = null

        fun getInstance() : GetApiData {

            if (getApiService ==null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiClientInstance.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                getApiService = retrofit.create(GetApiData::class.java)
            }
            return getApiService!!
        }
    }

}