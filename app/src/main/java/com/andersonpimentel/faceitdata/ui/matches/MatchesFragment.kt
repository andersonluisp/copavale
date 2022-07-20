package com.andersonpimentel.faceitdata.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.faceitdata.R
import com.andersonpimentel.faceitdata.data.models.championship.Championship
import com.andersonpimentel.faceitdata.data.repository.Repository
import com.andersonpimentel.faceitdata.databinding.FragmentMatchesBinding
import com.andersonpimentel.faceitdata.ui.matches.adapter.MatchesAdapter
import com.andersonpimentel.faceitdata.data.GetApiData
import com.andersonpimentel.faceitdata.data.models.matches.Match
import kotlinx.android.synthetic.main.fragment_matches.view.*

class MatchesFragment(
    val selectedChampionship: Championship,
    val listMatches: ArrayList<Match>,
    val filter: String
) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val getApiService = GetApiData.getInstance()
    private var listIsEmpty = true

    private lateinit var matchesViewModel: MatchesViewModel
    private var _binding: FragmentMatchesBinding? = null
    val adapter = MatchesAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        matchesViewModel =
            ViewModelProvider(this, MatchesViewModelFactory(Repository(getApiService))).get(MatchesViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_matches, container, false)
        bindViews(view)
        view.recycler_matches.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews(view: View){
        recyclerView = view.findViewById(R.id.recycler_matches)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        adapter.setChampionshipList(listMatches, filter)



    }
}