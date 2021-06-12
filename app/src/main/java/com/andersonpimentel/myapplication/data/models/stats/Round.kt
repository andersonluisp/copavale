package com.andersonpimentel.myapplication.data.models.stats

data class Round(
    val best_of: String,
    val game_id: String,
    val game_mode: String,
    val match_id: String,
    val round_stats: RoundStats,
    val teams: List<Team>
)