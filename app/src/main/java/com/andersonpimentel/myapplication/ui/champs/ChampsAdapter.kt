package com.andersonpimentel.myapplication.ui.champs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.championship.Championship
import com.andersonpimentel.myapplication.ui.champs.ChampsAdapter.*
import kotlinx.android.synthetic.main.championship_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class ChampsAdapter: RecyclerView.Adapter<ChampsAdapterViewHolder>() {
    private var championshipsList = arrayListOf<Championship>()
    class ChampsAdapterViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampsAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.championship_layout, parent, false)
        return ChampsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChampsAdapterViewHolder, position: Int) {
        //championships.sortByDescending { it.championship_start }
        holder.itemView.tv_championship_name.text = championshipsList[position].name

        if (championshipsList[position].status == "finished") {
            holder.itemView.tv_status_champ.text = "Finished"
            holder.itemView.tv_start_date.visibility = View.GONE
        } else {
            holder.itemView.tv_status_champ.text = "Começa"
            holder.itemView.tv_start_date.text =
                getShortDate(championshipsList[position].championship_start)
        }

        holder.itemView.card_championship.setOnClickListener{
            val direction = TabMenuChampFragmentDirections.actionNavHomeToNavTabMenuMatches(
                championshipsList[position]
            )
            holder.itemView.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return championshipsList.size
    }

    fun setChampionshipList(list: ArrayList<Championship>, filter: String){
        val oldList = championshipsList
        val diffCallBack = ChampionshipItemDiffCallBack(this.championshipsList, list)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallBack)
        championshipsList.clear()
        championshipsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
        //notifyDataSetChanged()
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

class ChampionshipItemDiffCallBack(
    var oldChampList: ArrayList<Championship>,
    var newChampList: ArrayList<Championship>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldChampList.size
        }

    override fun getNewListSize(): Int {
        return newChampList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldChampList.get(oldItemPosition).championship_id == newChampList.get(newItemPosition).championship_id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldChampList.get(oldItemPosition).equals(newChampList.get(newItemPosition))
    }

}