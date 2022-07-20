package com.andersonpimentel.faceitdata.data.models.matches

data class Match(
    var match_id: String,
    var competition_id: String,
    var competition_name: String,
    var scheduled_at: Long,
    var chat_room_id: String,
    var teams: Teams,
    var started_at: Long,
    var status: String,
    var results: Results,
    var best_of: Int
)

fun filterMatchStatus(inputArray: ArrayList<Match>, status: String): ArrayList<Match>{
    val filteredArray = arrayListOf<Match>()
    inputArray.forEach {
        if (it.status != "cancelled") {
            when (status) {
                "Upcoming" -> {
                    if (it.status == "SCHEDULED" || it.status == "CANCELLED") {
                        filteredArray.add(it)
                        filteredArray.sortBy { it.scheduled_at }
                    }
                }
                "Ongoing" -> {
                    if (it.status == "VOTING" || it.status == "CONFIGURING"|| it.status == "READY"|| it.status == "ONGOING") {
                        filteredArray.add(it)
                        filteredArray.sortBy { it.started_at }
                    }
                }
                else -> {
                    if (it.status == "FINISHED")
                        filteredArray.add(it)
                    filteredArray.sortByDescending { it.started_at }
                }
            }
        }
    }
    return filteredArray
}

fun filterMatchesBye(inputArray: ArrayList<Match>): ArrayList<Match> {
    val filteredArray = arrayListOf<Match>()
    inputArray.forEach {
        if (it.teams.faction1.name != "bye" && it.teams.faction2.name != "bye") {
            filteredArray.add(it)
        }
    }
    return filteredArray
}