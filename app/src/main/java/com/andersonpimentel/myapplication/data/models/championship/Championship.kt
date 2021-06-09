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
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass){
            return false
        }
        other as Championship
        if (championship_id != other.championship_id){
            return false
        }
        if (name != other.name){
            return false
        }
        if (status != other.status){
            return false
        }
        if (faceit_url != other.faceit_url){
            return false
        }
        return true
    }
}

fun filterStatus(inputArray: ArrayList<Championship>, status: String): ArrayList<Championship> {
    val filteredArray = arrayListOf<Championship>()
    inputArray.forEach {
        if (it.status != "cancelled") {
            when (status) {
                "Upcoming" -> {
                    if (it.status == "created" || it.status == "join" || it.status == "adjustment") {
                        filteredArray.add(it)
                    }
                }
                "Ongoing" -> {
                    if (it.status == "started") {
                        filteredArray.add(it)
                    }
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
