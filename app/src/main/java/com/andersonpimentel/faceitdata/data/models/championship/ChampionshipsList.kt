package com.andersonpimentel.faceitdata.data.models.championship

import com.google.gson.annotations.SerializedName

data class ChampionshipsList(
    @SerializedName("items")
    var items: List<Championship>
)
