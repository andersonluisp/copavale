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

fun filterStatus(inputArray: ArrayList<Championship>, status: String): ArrayList<Championship>{
    val filteredArray = arrayListOf<Championship>()
    inputArray.forEach {
        if (it.status != "cancelled") {
            when (status) {
                "Upcoming" -> {
                    if (it.status == "created") {
                        filteredArray.add(it)
                    }
                }
                "Ongoing" -> {
                    filteredArray.add(it)
                }
                else -> {
                    if (it.status == "finished")
                        filteredArray.add(it)
                }
            }
        }
    }
    return filteredArray
}
