package com.andersonpimentel.myapplication.data.models.championship

import com.andersonpimentel.myapplication.data.models.matches.Match
import com.google.gson.annotations.SerializedName

data class ChampionshipsList(
    @SerializedName("items")
    var items: List<Championship>
)
