package com.andersonpimentel.myapplication.data.models.stats

data class Team(
    val players: List<Player>,
    val team_id: String,
    val team_stats: TeamStats
)