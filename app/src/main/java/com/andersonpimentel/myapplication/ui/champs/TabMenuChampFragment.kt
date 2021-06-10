package com.andersonpimentel.myapplication.ui.champs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andersonpimentel.myapplication.data.repository.Repository
import com.andersonpimentel.myapplication.databinding.FragmentHomeBinding
import com.andersonpimentel.myapplication.ui.SharedViewModel
import com.andersonpimentel.myapplication.ui.SharedViewModelFactory
import com.andersonpimentel.myapplication.data.GetApiData
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.view.*

class TabMenuChampFragment : Fragment() {

    private lateinit var mSharedViewModel: SharedViewModel
    private var _binding: FragmentHomeBinding? = null
    private val getApiService = GetApiData.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mSharedViewModel =
            ViewModelProvider(this, SharedViewModelFactory(Repository(getApiService))).get(SharedViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val listMenu = arrayListOf("Upcoming", "Ongoing", "Past")

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