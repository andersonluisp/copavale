package com.andersonpimentel.myapplication.data.models.stats

import com.google.gson.annotations.SerializedName

data class PlayerStats(
    @SerializedName("Assists")
    val assists: String,
    @SerializedName("Deaths")
    val deaths: String,
    @SerializedName("Headshots")
    val headshots: String,
    @SerializedName("Headshots %")
    val headshots_percent: String,
    @SerializedName("K/D Ratio")
    val k_d_ratio: String,
    @SerializedName("K/R Ratio")
    val k_r_ratio: String,
    @SerializedName("Kills")
    val kills: String,
    @SerializedName("MVPs")
    val mvps: String,
    @SerializedName("Penta Kills")
    val penta_kills: String,
    @SerializedName("Quadro Kills")
    val quadro_kills: String,
    @SerializedName("Result")
    val result: String,
    @SerializedName("Triple Kills")
    val triple_kills: String
)