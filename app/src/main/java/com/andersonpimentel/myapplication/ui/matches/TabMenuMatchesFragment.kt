package com.andersonpimentel.myapplication.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.GetApiData
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.matches.Match
import com.andersonpimentel.myapplication.data.repository.Repository
import com.andersonpimentel.myapplication.databinding.FragmentTabMenuMatchesBinding
import com.andersonpimentel.myapplication.ui.champs.ChampsDetailFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tab_menu_matches.*
import kotlinx.android.synthetic.main.fragment_tab_menu_matches.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TabMenuMatchesFragment : Fragment() {

    val args: TabMenuMatchesFragmentArgs by navArgs()
    private var _binding: FragmentTabMenuMatchesBinding? = null
    lateinit var championshipList: Championship
    private lateinit var mMatchesViewModel: MatchesViewModel
    private val getApiService = GetApiData.getInstance()

    private val binding get() = _binding!!
    private var listMenu = arrayListOf<String?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mMatchesViewModel = ViewModelProvider(this, MatchesViewModelFactory(Repository(getApiService))).get(MatchesViewModel::class.java)

        _binding = FragmentTabMenuMatchesBinding.inflate(inflater, container, false)

        listMenu = arrayListOf(
            getString(R.string.details),
            getString(R.string.upcoming_matches),
            getString(R.string.ongoing_matches),
            getString(R.string.results)
        )

        val view = inflater.inflate(R.layout.fragment_tab_menu_matches, container, false)
        view.tv_championship_name_tab_matches.text = args.championshipDetails.name

        val root: View = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mMatchesViewModel.listMatch.isNullOrEmpty()){
            progress_circular_tab_matches.visibility = View.VISIBLE
            tv_loading_data_tab_matches.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                mMatchesViewModel.getMatches(args.championshipDetails.championship_id,mMatchesViewModel.controlOffset.toString())
                                mMatchesViewModel.matchesListData.postValue(mMatchesViewModel.listMatch)
                withContext(Dispatchers.Main) {
                    progress_circular_tab_matches.visibility = View.GONE
                    tv_loading_data_tab_matches.visibility = View.GONE
                    view.match_viewpager.adapter = FragmentTypeAdapter(requireParentFragment(), args.championshipDetails, mMatchesViewModel.listMatch)
                    val status = args.championshipDetails.status
                    when (status) {
                        "finished" -> {
                            listMenu = arrayListOf(getString(R.string.details), getString(R.string.results))
                            TabLayoutMediator(view.matches_tab_menu, view.match_viewpager) { tab, position ->
                                tab.text = listMenu[position]
                            }.attach()
                        }
                        "adjustement" -> {
                            listMenu = arrayListOf(getString(R.string.details), getString(R.string.upcoming_matches))
                            TabLayoutMediator(view.matches_tab_menu, view.match_viewpager) { tab, position ->
                                tab.text = listMenu[position]
                            }.attach()
                        }
                        else -> {
                            TabLayoutMediator(view.matches_tab_menu, view.match_viewpager) { tab, position ->
                                tab.text = listMenu[position]
                            }.attach()
                        }
                    }
                }
            }
        } else{
            view.match_viewpager.adapter = FragmentTypeAdapter(
                requireParentFragment(),
                args.championshipDetails,
                mMatchesViewModel.listMatch
            )
            TabLayoutMediator(view.matches_tab_menu, view.match_viewpager){tab, position ->
                tab.text = listMenu[position]
            }.attach()
        }

    }


    class FragmentTypeAdapter(fragment: Fragment, val championship: Championship, val matches: ArrayList<Match>) :
        FragmentStateAdapter(fragment) {
        val status = championship.status

        override fun getItemCount(): Int {
            return if (status == "finished" || status == "adjustement") {
                2
            } else {
                4
            }
        }

        override fun createFragment(position: Int): Fragment {
            return when (status) {
                "finished" -> {
                    when (position) {
                        0 -> ChampsDetailFragment(championship)
                        else -> MatchesFragment(championship, matches, "Finished")
                    }
                }
                "adjustement" -> {
                    when (position) {
                        0 -> ChampsDetailFragment(championship)
                        else -> MatchesFragment(championship, matches, "Upcoming")
                    }
                }
                else -> {
                    when (position) {
                        0 -> ChampsDetailFragment(championship)
                        1 -> MatchesFragment(championship, matches, "Upcoming")
                        2 -> MatchesFragment(championship, matches, "Ongoing")
                        else -> MatchesFragment(championship, matches, "Finished")
                    }
                }
            }
        }
    }
}