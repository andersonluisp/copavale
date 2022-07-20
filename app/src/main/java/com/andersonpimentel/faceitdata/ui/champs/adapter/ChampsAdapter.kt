package com.andersonpimentel.faceitdata.ui.champs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.faceitdata.R
import com.andersonpimentel.faceitdata.data.models.championship.Championship
import com.andersonpimentel.faceitdata.data.models.championship.filterStatus
import com.andersonpimentel.faceitdata.ui.champs.TabMenuChampFragmentDirections
import com.andersonpimentel.faceitdata.ui.champs.adapter.ChampsAdapter.*
import kotlinx.android.synthetic.main.championship_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class ChampsAdapter: RecyclerView.Adapter<ChampsAdapterViewHolder>() {
    private var championships = arrayListOf<Championship>()
    class ChampsAdapterViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampsAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.championship_layout, parent, false)
        return ChampsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChampsAdapterViewHolder, position: Int) {
        holder.itemView.tv_championship_name.text = championships[position].name

        if (championships[position].status == "finished") {
            holder.itemView.tv_status_champ.text = holder.itemView.context.getString(R.string.finished)
            holder.itemView.tv_start_date.visibility = View.GONE
            holder.itemView.tv_start_hour.visibility = View.GONE
        } else {
            holder.itemView.tv_status_champ.text = holder.itemView.context.getString(R.string.starts)
            holder.itemView.tv_start_date.text =
                parseTimestampToDate(championships[position].championship_start)
            holder.itemView.tv_start_hour.text =
                parseTimestampToHour(championships[position].championship_start)
        }

        holder.itemView.card_championship.setOnClickListener{
            val direction = TabMenuChampFragmentDirections.actionNavHomeToNavTabMenuMatches(
                championships[position]
            )
            holder.itemView.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return championships.size
    }

    fun setChampionshipList(list: ArrayList<Championship>, filter: String){
        championships.addAll(filterStatus(list, filter))
        notifyDataSetChanged()
    }

    fun parseTimestampToDateHour(ts:Long?):String{
        if(!(ts != null || ts == 0L)) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts - 3 * 3600000
        //return formatted date
        return android.text.format.DateFormat.format("dd/MM/yyy HH:mm", calendar).toString()
    }

    fun parseTimestampToDate(ts:Long?):String{
        if(!(ts != null || ts == 0L)) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts - 3 * 3600000
        //return formatted date
        return android.text.format.DateFormat.format("dd/MM/yyy", calendar).toString()
    }

    fun parseTimestampToHour(ts:Long?):String{
        if(!(ts != null || ts == 0L)) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts - 3 * 3600000
        //return formatted date
        return android.text.format.DateFormat.format("HH:mm", calendar).toString()
    }
}