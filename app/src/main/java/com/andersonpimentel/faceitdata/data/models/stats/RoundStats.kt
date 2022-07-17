package com.andersonpimentel.faceitdata.data.models.stats

import com.google.gson.annotations.SerializedName

data class RoundStats(
    @SerializedName("Map")
    val map: String,
    @SerializedName("Region")
    val region: String,
    @SerializedName("Rounds")
    val rounds: String,
    @SerializedName("Score")
    val score: String,
    @SerializedName("Winner")
    val winner: String
)