package com.andersonpimentel.myapplication.ui.champs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.championship.filterStatus
import com.andersonpimentel.myapplication.ui.champs.ChampsAdapter.*
import kotlinx.android.synthetic.main.championship_layout.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class ChampsAdapter: RecyclerView.Adapter<ChampsAdapterViewHolder>() {
    private var championships = arrayListOf<Championship>()

    class ChampsAdapterViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampsAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.championship_layout, parent, false)
        return ChampsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChampsAdapterViewHolder, position: Int) {
            championships.sortByDescending { it.championship_start }
            holder.itemView.tv_championship_name.text = championships[position].name
    }

    override fun getItemCount(): Int {
        return championships.size
    }

    fun setChampionshipList(list: ArrayList<Championship>, filter: String){
        championships.addAll(filterStatus(list, filter))
        notifyDataSetChanged()
    }

    fun getShortDate(ts:Long?):String{
        if(!(ts != null || ts == 0L)) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts * 1000 - 3 * 3600000
        //return formatted date
        return android.text.format.DateFormat.format("dd/MM/yyy HH:mm", calendar).toString()
    }
}