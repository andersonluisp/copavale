package com.andersonpimentel.myapplication.ui.champs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.repository.Repository
import com.andersonpimentel.myapplication.databinding.FragmentTabMenuChampionshipsBinding
import com.andersonpimentel.myapplication.ui.SharedViewModel
import com.andersonpimentel.myapplication.ui.SharedViewModelFactory
import com.andersonpimentel.myapplication.data.GetApiData
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.ui.matches.TabMenuMatchesFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tab_menu_championships.*
import kotlinx.android.synthetic.main.fragment_tab_menu_championships.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class TabMenuChampFragment : Fragment() {

    private lateinit var mChampsViewModel: ChampsViewModel
    private var _binding: FragmentTabMenuChampionshipsBinding? = null
    private val getApiService = GetApiData.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var listMenu = arrayListOf<String?>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mChampsViewModel =
            ViewModelProvider(this, ChampsViewModelFactory(Repository(getApiService))).get(ChampsViewModel::class.java)

        _binding = FragmentTabMenuChampionshipsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        listMenu = arrayListOf(getString(R.string.upcoming), getString(R.string.ongoing), getString(R.string.past))


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mChampsViewModel.listChampionship.isNullOrEmpty()){
            progress_circular.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE

        CoroutineScope(IO).launch {
            withTimeoutOrNull(20000){
                try{
                mChampsViewModel.getChampionship(mChampsViewModel.organizerId,mChampsViewModel.controlOffset.toString())
                mChampsViewModel.championshipData.postValue(mChampsViewModel.listChampionship)
                } catch(e: Exception){
                    Log.e("Timed out to get API", "$e")
                }
                withContext(Main) {
                    progress_circular.visibility = View.GONE
                    tv_loading_data.visibility = View.GONE
                    view.home_viewpager.adapter = TabMenuChampFragment.FragmentTypeAdapter(
                        requireParentFragment(),
                        mChampsViewModel.listChampionship
                    )
                    TabLayoutMediator(view.home_tab_menu, view.home_viewpager){tab, position ->
                        tab.text = listMenu[position]
                    }.attach()
                }
            }
        }
        } else{
            view.home_viewpager.adapter = FragmentTypeAdapter(requireParentFragment(), mChampsViewModel.listChampionship)
            TabLayoutMediator(view.home_tab_menu, view.home_viewpager){tab, position ->
                tab.text = listMenu[position]
            }.attach()
        }


    }



    class FragmentTypeAdapter(fragment: Fragment, val championships: ArrayList<Championship>) : FragmentStateAdapter(fragment){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ChampsFragments("Upcoming", championships)
                1 -> ChampsFragments("Ongoing", championships)
                else -> ChampsFragments("Finished", championships)
            }
            }

    }
}