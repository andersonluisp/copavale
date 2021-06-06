package com.andersonpimentel.myapplication.data.models.matches

data class Match(
    var match_id: String,
    var competition_id: String,
    var competition_name: String,
    var scheduled_at: Long,
    var chat_room_id: String,
    var teams: Teams,
    var started_at: Long,
    var status: String
)
