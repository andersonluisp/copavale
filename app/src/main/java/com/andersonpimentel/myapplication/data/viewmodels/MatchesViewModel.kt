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
    private var controlOffset = 0

    fun getMatches(id: String, offset: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val call = repository.getAllMatches(id, offset, "100")
            call.enqueue(object : Callback<MatchesList> {
                override fun onResponse(
                    call: Call<MatchesList>,
                    response: Response<MatchesList>
                ) {
                    for (i in 0 until response.body()!!.items.size) {
                        listMatch.add(response.body()!!.items[i])
                    }
                    matchesListData.postValue(listMatch)
                    if (listMatch.size == controlOffset + 100) {
                        controlOffset += 100
                        getMatches(id, controlOffset.toString())
                    }
                }

                override fun onFailure(call: Call<MatchesList>, t: Throwable) {
                    Toast.makeText(matchesFragment.context, "Error reading JSON", Toast.LENGTH_LONG)
                        .show()
                }
            })

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