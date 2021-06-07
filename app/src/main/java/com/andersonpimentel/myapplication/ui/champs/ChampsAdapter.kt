package com.andersonpimentel.myapplication.ui.champs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.data.models.championship.filterStatus
import com.andersonpimentel.myapplication.ui.champs.ChampsAdapter.*
import kotlinx.android.synthetic.main.championship_layout.view.*
import kotlinx.android.synthetic.main.fragment_champs.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
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

        if (championships[position].status == "finished") {
            holder.itemView.tv_status_champ.text = "Finished"
            holder.itemView.tv_start_date.visibility = View.GONE
        } else {
            holder.itemView.tv_status_champ.text = "Começa"
            holder.itemView.tv_start_date.text =
                getShortDate(championships[position].championship_start)
        }
        holder.itemView.card_championship.setOnClickListener{
            val direction = TabMenuChampFragmentDirections.actionNavHomeToNavTabMenuMatches(championships[position].championship_id, championships[position].name)
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