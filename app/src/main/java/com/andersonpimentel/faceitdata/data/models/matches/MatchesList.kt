package com.andersonpimentel.faceitdata.data.models.matches

import com.google.gson.annotations.SerializedName

data class MatchesList(
    @SerializedName("items")
    var items: List<Match>
)
