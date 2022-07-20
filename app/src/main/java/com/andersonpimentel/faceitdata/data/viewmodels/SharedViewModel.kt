package com.andersonpimentel.faceitdata.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andersonpimentel.faceitdata.data.models.championship.Championship
import com.andersonpimentel.faceitdata.data.models.matches.Match
import com.andersonpimentel.faceitdata.data.models.matches.MatchesList
import com.andersonpimentel.faceitdata.data.repository.Repository

class SharedViewModel constructor(private val repository: Repository) : ViewModel() {

    var matchesListData = MutableLiveData<MatchesList>()
    var sharedChampionshipData = MutableLiveData<ArrayList<Championship>>()
    var listMatch = arrayListOf<Match>()
    var listChampionship = arrayListOf<Championship>()

    var controlOffset = 0
    val organizerId = "4f3dba1e-2f54-49b4-bfea-e03a7d345505"

    init{

    }

    suspend fun getChampionship(id: String, offset: String) {
        val call = repository.getAllChamps(id, offset, "100")
        for (i in 0 until call.items.size) {
            listChampionship.add(call.items[i])
        }
        if (listChampionship.size == controlOffset + 100) {
            controlOffset += 100
            getChampionship(id, controlOffset.toString())
        }
    }
}

class SharedViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            SharedViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }
}