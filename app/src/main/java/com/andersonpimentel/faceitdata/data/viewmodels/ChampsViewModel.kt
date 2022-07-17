package com.andersonpimentel.faceitdata.ui.champs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andersonpimentel.faceitdata.data.models.championship.Championship
import com.andersonpimentel.faceitdata.data.models.matches.Match
import com.andersonpimentel.faceitdata.data.models.matches.MatchesList
import com.andersonpimentel.faceitdata.data.repository.Repository

class ChampsViewModel constructor(private val repository: Repository) : ViewModel() {

    var matchesListData = MutableLiveData<MatchesList>()
    var championshipData = MutableLiveData<List<Championship>>()
    var listMatch = arrayListOf<Match>()
    var listChampionship = arrayListOf<Championship>()
    var controlOffset = 0

    val organizerId = "4f3dba1e-2f54-49b4-bfea-e03a7d345505"

    init{
    }

    suspend fun getChampionship(id: String,) {
        do {
            val response = repository.getAllChamps(id, controlOffset.toString(), "100")
            listChampionship.addAll(response.items)
            controlOffset += response.items.size
        } while (controlOffset % 100 == 0)
        championshipData.postValue(listChampionship)
    }
}

class ChampsViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChampsViewModel::class.java)) {
            ChampsViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }
}