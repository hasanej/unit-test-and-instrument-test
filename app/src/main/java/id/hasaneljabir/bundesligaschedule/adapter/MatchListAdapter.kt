package id.hasaneljabir.bundesligaschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.bundesligaschedule.R
import id.hasaneljabir.bundesligaschedule.activity.DetailActivity
import id.hasaneljabir.bundesligaschedule.model.EventData
import id.hasaneljabir.bundesligaschedule.utils.DateUtil
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.startActivity

class MatchListAdapter(val eventList: List<EventData>, val context: Context?) :
    RecyclerView.Adapter<MatchListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: EventData) {
            if (event.intHomeScore == null) {
                itemView.txtDate.setTextColor(itemView.context.resources.getColor(R.color.colorDateNextMatch))
            }
            itemView.txtDate.text = event.dateEvent?.let { DateUtil.formatDateToMatch(it) }
            itemView.txtNameHome.text = event.strHomeTeam
            itemView.txtNameAway.text = event.strAwayTeam
            itemView.txtScoreHome.text = event.intHomeScore
            itemView.txtScoreAway.text = event.intAwayScore

            itemView.setOnClickListener {
                itemView.context.startActivity<DetailActivity>("match" to event)
            }
        }
    }
}