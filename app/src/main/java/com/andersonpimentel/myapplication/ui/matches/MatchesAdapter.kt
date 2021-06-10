package com.andersonpimentel.myapplication.ui.matches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andersonpimentel.myapplication.R
import com.andersonpimentel.myapplication.data.models.matches.Match
import com.andersonpimentel.myapplication.data.models.matches.filterMatchStatus
import com.andersonpimentel.myapplication.data.models.matches.filterMatchesBye
import com.andersonpimentel.myapplication.ui.matches.MatchesAdapter.*
import kotlinx.android.synthetic.main.match_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class MatchesAdapter: RecyclerView.Adapter<MatchesAdapterViewHolder>() {
    private var matchesList = arrayListOf<Match>()
    class MatchesAdapterViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.match_layout, parent, false)
        return MatchesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesAdapterViewHolder, position: Int) {
        holder.itemView.tv_team1.text = matchesList[position].teams.faction1.name
        holder.itemView.tv_team2.text = matchesList[position].teams.faction2.name

        when {
            matchesList[position].started_at == 0L && matchesList[position].status == "FINISHED" -> {
                holder.itemView.tv_date.text = "WO"
            }
            matchesList[position].status == "FINISHED" -> {
                holder.itemView.tv_date.text = getShortDate(matchesList[position].started_at)
            }
            else -> {
                holder.itemView.tv_date.text = getShortDate(matchesList[position].scheduled_at)
            }
        }

        holder.itemView.iv_team1_logo.load(matchesList[position].teams.faction1.avatar) {
            //Exibe uma imagem enquanto a imagem não foi renderizada
            placeholder(R.drawable.ic_baseline_insert_photo_24)
            fallback(R.drawable.ic_baseline_insert_photo_24)
            error(R.drawable.img_without_logo)
        }


        holder.itemView.iv_team2_logo.load(matchesList[position].teams.faction2.avatar) {
            //Exibe uma imagem enquanto a logo não foi renderizada
            placeholder(R.drawable.ic_baseline_insert_photo_24)
            fallback(R.drawable.ic_baseline_insert_photo_24)
            error(R.drawable.img_without_logo)
        }

    }

    override fun getItemCount(): Int {
        return matchesList.size
    }

    fun setChampionshipList(list: ArrayList<Match>, filter: String) {
        matchesList.addAll(filterMatchStatus(filterMatchesBye(list), filter))
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