package com.andersonpimentel.myapplication.data.models.championship

data class Championship(
    var championship_id: String,
    var name: String,
    var description: String,
    var type: String,
    var status: String,
    var subscription_start: Long,
    var subscription_end: Long,
    var championship_start: Long,
    var slots: Int,
    var current_subscriptions: Int,
    var faceit_url: String
)
