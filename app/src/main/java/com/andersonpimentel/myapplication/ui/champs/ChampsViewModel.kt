package com.andersonpimentel.myapplication.ui.champs

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.championship.ChampionshipsList
import com.andersonpimentel.myapplication.data.models.matches.Match
import com.andersonpimentel.myapplication.data.models.matches.MatchesList
import com.andersonpimentel.myapplication.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChampsViewModel constructor(private val repository: Repository) : ViewModel() {

    var matchesListData = MutableLiveData<MatchesList>()
    var championshipData = MutableLiveData<ArrayList<Championship>>()
    var listMatch = arrayListOf<Match>()
    var listChampionship = arrayListOf<Championship>()

    private lateinit var champsFragment: ChampsFragments

    fun getChampionship(id: String, offset: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val call = repository.getAllChamps(id, offset, "100")
            call.enqueue(object : Callback<ChampionshipsList> {
                override fun onResponse(
                    call: Call<ChampionshipsList>,
                    response: Response<ChampionshipsList>
                ) {
                    for (i in 0 until response.body()!!.items.size) {
                        listChampionship.add(response.body()!!.items[i])
                    }
                    championshipData.postValue(listChampionship)
                    if (listChampionship.size == 100) {
                        getChampionship(id, "100")
                    }
                }

                override fun onFailure(call: Call<ChampionshipsList>, t: Throwable) {
                    Toast.makeText(champsFragment.context, "Error reading JSON", Toast.LENGTH_LONG)
                        .show()
                }
            })

        }
    }
}

class ChampsViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChampsViewModel::class.java)) {
            ChampsViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }

}