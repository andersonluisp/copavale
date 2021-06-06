package com.andersonpimentel.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.championship.ChampionshipsList
import com.andersonpimentel.myapplication.data.models.matches.MatchesList
import com.andersonpimentel.myapplication.utils.ApiClientInstance
import com.andersonpimentel.myapplication.utils.GetApiData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository constructor(private val getApiData: GetApiData) {

    fun getAllChamps(id: String, offset: String, limit: String) =
        getApiData.getChampionships(id, offset, limit)

}