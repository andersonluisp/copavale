package com.andersonpimentel.myapplication.data.models.championship

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable

fun filterStatus(inputArray: ArrayList<Championship>, status: String): ArrayList<Championship>{
    val filteredArray = arrayListOf<Championship>()
    inputArray.forEach {
        if (it.status != "cancelled") {
            when (status) {
                "Upcoming" -> {
                    if (it.status == "created" || it.status == "join" || it.status == "adjustment" ) {
                        filteredArray.add(it)
                        filteredArray.sortedBy { it.championship_start }
                    }
                }
                "Ongoing" -> {
                    if (it.status == "started") {
                        filteredArray.add(it)
                        filteredArray.sortedBy { it.championship_start }
                    }
                }
                "Past" -> {
                    if (it.status == "finished") {
                        filteredArray.add(it)
                        filteredArray.sortedByDescending { it.subscription_start }
                    }
                }
                else -> {
                    if (it.status == "finished") {
                        filteredArray.add(it)
                        filteredArray.sortedByDescending { it.subscription_start }
                    }
                }
            }
        }
    }
    return filteredArray
}
