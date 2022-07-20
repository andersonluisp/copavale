package com.andersonpimentel.faceitdata.ui.champs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.faceitdata.R
import com.andersonpimentel.faceitdata.databinding.FragmentChampsBinding
import com.andersonpimentel.faceitdata.ui.champs.adapter.ChampsAdapter
import com.andersonpimentel.faceitdata.data.models.championship.Championship

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