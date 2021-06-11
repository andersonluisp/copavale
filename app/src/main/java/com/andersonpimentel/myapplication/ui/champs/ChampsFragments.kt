package com.andersonpimentel.myapplication.ui.champs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.repository.Repository
import com.andersonpimentel.myapplication.databinding.FragmentChampsBinding
import com.andersonpimentel.myapplication.ui.champs.adapter.ChampsAdapter
import com.andersonpimentel.myapplication.data.GetApiData
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.ui.SharedViewModel
import com.andersonpimentel.myapplication.ui.SharedViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChampsFragments(val filter: String, val champsList: ArrayList<Championship>) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var listIsEmpty = true
    private var _binding: FragmentChampsBinding? = null
    val adapter = ChampsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_champs, container, false)

        _binding = FragmentChampsBinding.inflate(inflater, container, false)
        _binding!!.recyclerChampionships.adapter = adapter

        bindViews(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews(view: View){
        recyclerView = view.findViewById(R.id.recycler_championships)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        if (listIsEmpty == true) {
            adapter.setChampionshipList(champsList, filter)
        }
        listIsEmpty = false

    }

}