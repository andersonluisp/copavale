package com.andersonpimentel.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.databinding.FragmentHomeBinding
import com.andersonpimentel.myapplication.ui.champs.ChampsAdapter
import com.andersonpimentel.myapplication.ui.champs.ChampsFragments
import com.andersonpimentel.myapplication.ui.matches.MatchesFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mhomeViewModel: HomeViewModel
    private val champsAdapter: ChampsAdapter by lazy { ChampsAdapter() }
    private var _binding: FragmentHomeBinding? = null
    var listChampionship = arrayListOf<Championship>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mhomeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val listMenu = arrayListOf("Upcoming", "Ongoing", "Past")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.home_viewpager.adapter = FragmentTypeAdapter(this)

        TabLayoutMediator(view.home_tab_menu, view.home_viewpager){tab, position ->
            tab.text = listMenu[position]
        }.attach()
    }



    class FragmentTypeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ChampsFragments("Upcoming")
                1 -> ChampsFragments("Ongoing")
                else -> ChampsFragments("Past")
            }
            }

    }
}