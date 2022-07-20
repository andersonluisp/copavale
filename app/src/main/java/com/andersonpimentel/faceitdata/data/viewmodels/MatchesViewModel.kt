package com.andersonpimentel.faceitdata.ui.matches

import androidx.lifecycle.*
import com.andersonpimentel.faceitdata.data.models.championship.Championship
import com.andersonpimentel.faceitdata.data.models.matches.Match
import com.andersonpimentel.faceitdata.data.repository.Repository

class MatchesViewModel(private val repository: Repository) : ViewModel() {

    var matchesListData = MutableLiveData<ArrayList<Match>>()
    var championshipData = MutableLiveData<ArrayList<Championship>>()
    var listMatch = arrayListOf<Match>()
    var listChampionship = arrayListOf<Championship>()

    private lateinit var matchesFragment: MatchesFragment
    var controlOffset = 0

    suspend fun getMatches(id: String, offset: String) {
        val call = repository.getAllMatches(id, offset, "100")
        for (i in 0 until call.items.size) {
            listMatch.add(call.items[i])
        }
        matchesListData.postValue(listMatch)
        if (listMatch.size == controlOffset + 100) {
            controlOffset += 100
            getMatches(id, controlOffset.toString())
        }
    }
}

class MatchesViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            MatchesViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }
}