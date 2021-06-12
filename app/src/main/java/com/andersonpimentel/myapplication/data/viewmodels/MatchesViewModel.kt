package com.andersonpimentel.myapplication.ui.matches

import android.widget.Toast
import androidx.lifecycle.*
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.matches.Match
import com.andersonpimentel.myapplication.data.models.matches.MatchesList
import com.andersonpimentel.myapplication.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            MatchesViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }

}