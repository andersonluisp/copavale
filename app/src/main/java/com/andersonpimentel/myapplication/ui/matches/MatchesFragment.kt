package com.andersonpimentel.myapplication.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.databinding.FragmentMatchesBinding

class MatchesFragment(
    val selectedChampionship: Championship
) : Fragment() {

    private lateinit var matchesViewModel: MatchesViewModel
    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        matchesViewModel =
            ViewModelProvider(this).get(MatchesViewModel::class.java)

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        matchesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}