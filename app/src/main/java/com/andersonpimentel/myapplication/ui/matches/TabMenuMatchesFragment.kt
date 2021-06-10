package com.andersonpimentel.myapplication.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.databinding.FragmentTabMenuMatchesBinding
import com.andersonpimentel.myapplication.ui.champs.ChampsDetailFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tab_menu_matches.view.*

class TabMenuMatchesFragment : Fragment() {

    val args: TabMenuMatchesFragmentArgs by navArgs()
    private var _binding: FragmentTabMenuMatchesBinding? = null
    lateinit var championshipList: Championship


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabMenuMatchesBinding.inflate(inflater, container, false)

        val view = inflater.inflate(R.layout.fragment_tab_menu_matches, container, false)
        view.tv_championship_name_tab_matches.text = args.championshipDetails.name

        val root: View = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    var listMenu = arrayListOf("Details", "Upcoming\n matches", "Ongoing\n matches", "Results")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.match_viewpager.adapter =
            FragmentTypeAdapter(this, args.championshipDetails)


        val status = args.championshipDetails.status

        when (status) {
            "finished" -> {
                listMenu = arrayListOf("Details", "Results")
                TabLayoutMediator(view.matches_tab_menu, view.match_viewpager) { tab, position ->
                    tab.text = listMenu[position]
                }.attach()
            }
            "adjustement" -> {
                listMenu = arrayListOf("Details", "Upcoming\n matches")
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


    class FragmentTypeAdapter(fragment: Fragment, championship: Championship) :
        FragmentStateAdapter(fragment) {
        private val selectedChampionship = championship
        val status = selectedChampionship.status

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
                        0 -> ChampsDetailFragment(selectedChampionship)
                        else -> MatchesFragment(selectedChampionship, "Finished")
                    }
                }
                "adjustement" -> {
                    when (position) {
                        0 -> ChampsDetailFragment(selectedChampionship)
                        else -> MatchesFragment(selectedChampionship, "Upcoming")
                    }
                }
                else -> {
                    when (position) {
                        0 -> ChampsDetailFragment(selectedChampionship)
                        1 -> MatchesFragment(selectedChampionship, "Upcoming")
                        2 -> MatchesFragment(selectedChampionship, "Ongoing")
                        else -> MatchesFragment(selectedChampionship, "Finished")
                    }
                }
            }
        }
    }
}