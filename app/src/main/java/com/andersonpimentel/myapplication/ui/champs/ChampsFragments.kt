package com.andersonpimentel.myapplication.ui.champs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.repository.Repository
import com.andersonpimentel.myapplication.databinding.FragmentChampsBinding
import com.andersonpimentel.myapplication.utils.GetApiData

class ChampsFragments(val filter: String) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var champsViewModel: ChampsViewModel
    private val getApiService = GetApiData.getInstance()
    private var listIsEmpty = true

    private var _binding: FragmentChampsBinding? = null
    val adapter = ChampsAdapter()
    val organizerId = "4f3dba1e-2f54-49b4-bfea-e03a7d345505"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        champsViewModel =
            ViewModelProvider(this, ChampsViewModelFactory(Repository(getApiService))).get(ChampsViewModel::class.java)

        _binding = FragmentChampsBinding.inflate(inflater, container, false)
        _binding!!.recyclerChampionships.adapter = adapter

        val view =  inflater.inflate(R.layout.fragment_champs, container, false)
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
            //champsViewModel.getChampionship(organizerId, "0")

            champsViewModel.championshipData.observe(viewLifecycleOwner, { data ->
                //Log.d("ChampsFragment", "onCreate: $data")
                adapter.setChampionshipList(data, filter)
            })
        }
        listIsEmpty = false

    }

}