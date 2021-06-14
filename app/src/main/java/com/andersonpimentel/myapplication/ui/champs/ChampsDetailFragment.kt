package com.andersonpimentel.myapplication.ui.champs

import android.app.Application
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import kotlinx.android.synthetic.main.fragment_champs_detail.view.*
import java.util.*

class ChampsDetailFragment(
    val selectedChampionship: Championship
    ) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_champs_detail, container, false)
        view.championship_description.text = selectedChampionship.description
        view.tv_slots.text = "${selectedChampionship.current_subscriptions} / ${selectedChampionship.slots}"
        view.tv_subscription_start.text = getShortDate(selectedChampionship.subscription_start)
        view.tv_subscription_ends.text = getShortDate(selectedChampionship.subscription_end)
        view.tv_status.text = selectedChampionship.status
        view.bt_subscribe.isClickable = false

        if (selectedChampionship.status == "join"){
            view.bt_subscribe.isClickable = true
            view.bt_subscribe.setBackgroundColor(resources.getColor(R.color.orange))
            view.bt_subscribe.text = getString(R.string.join_tournament)
            //TODO "Make event click listener button"
        }


        // Inflate the layout for this fragment
        return view
    }

    fun getShortDate(ts:Long?):String{
        if(!(ts != null || ts == 0L)) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts - 3 * 3600000
        //return formatted date
        return android.text.format.DateFormat.format("dd/MM/yyy HH:mm", calendar).toString()
    }

}