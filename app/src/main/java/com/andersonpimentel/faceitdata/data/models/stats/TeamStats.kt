package com.andersonpimentel.faceitdata.data.models.stats

import com.google.gson.annotations.SerializedName

data class TeamStats(
    @SerializedName("Final Score")
    val final_score: String,
    @SerializedName("First Half Score")
    val first_half_score: String,
    @SerializedName("Overtime score")
    val overtime_score: String,
    @SerializedName("Second Half Score")
    val second_half_score: String,
    @SerializedName("Team")
    val team: String,
    @SerializedName("Team Headshots")
    val team_headshots: String,
    @SerializedName("Team Win")
    val team_win: String
)