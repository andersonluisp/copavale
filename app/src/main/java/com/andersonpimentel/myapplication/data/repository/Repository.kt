package com.andersonpimentel.myapplication.data.repository

import com.andersonpimentel.myapplication.data.GetApiData

class Repository constructor(private val getApiData: GetApiData) {

    suspend fun getAllChamps(id: String, offset: String, limit: String) =
        getApiData.getChampionships(id, offset, limit)

    suspend fun getAllMatches(id: String, offset: String, limit: String) =
        getApiData.getMatches(id, offset, limit)

}