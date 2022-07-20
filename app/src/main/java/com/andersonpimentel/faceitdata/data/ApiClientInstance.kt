package com.andersonpimentel.faceitdata.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientInstance {

    var retrofit: Retrofit? = null
    val BASE_URL = "https://open.faceit.com/data/v4/"

    //create a retrofit instance
    val retrofitInstance: Retrofit?
    get() {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}