package com.andersonpimentel.myapplication.ui

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
import com.andersonpimentel.myapplication.ui.champs.ChampsFragments
import com.andersonpimentel.myapplication.ui.champs.ChampsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel constructor(private val repository: Repository) : ViewModel() {

    var matchesListData = MutableLiveData<MatchesList>()
    var sharedChampionshipData = MutableLiveData<ArrayList<Championship>>()
    var listMatch = arrayListOf<Match>()
    var listChampionship = arrayListOf<Championship>()

    private lateinit var champsFragment: ChampsFragments

    val organizerId = "4f3dba1e-2f54-49b4-bfea-e03a7d345505"

    init{
        //getChampionship(organizerId,"0")
    }

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
                    sharedChampionshipData.postValue(listChampionship)
                    if (listChampionship.size == 100) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 200) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 300) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 400) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 500) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 600) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 700) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 800) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 900) {
                        getChampionship(id, listChampionship.size.toString())
                    }
                    if (listChampionship.size == 1000) {
                        getChampionship(id, listChampionship.size.toString())
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

class SharedViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            SharedViewModel(this.repository) as T
        } else {
            throw IllegalAccessException("ViewModel Not Found")
        }
    }

}