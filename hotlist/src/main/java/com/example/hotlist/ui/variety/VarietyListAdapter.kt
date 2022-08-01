package com.example.hotlist.ui.variety

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotlist.R
import com.example.hotlist.bean.ListItem

class VarietyListAdapter(val varietyList: List<ListItem>) :
    RecyclerView.Adapter<VarietyListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VarietyListViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return VarietyListViewHolder(view)
    }

    override fun onBindViewHolder(holder: VarietyListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = varietyList.size
}


class VarietyListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageViewPoster: ImageView = itemView.findViewById(R.id.imageView_poster)
    var textName: TextView = itemView.findViewById(R.id.text_name)
    var textActors: TextView = itemView.findViewById(R.id.text_actors)
    var textEpisode: TextView = itemView.findViewById(R.id.text_episode)
    var textTime: TextView = itemView.findViewById(R.id.text_time)
    var textHot: TextView = itemView.findViewById(R.id.text_hot)


    fun bind(item: ListItem) {
        textName.text = item.name
        textTime.text = item.release_date
        textHot.text = item.discussion_hot.toString()
        item.actors.forEach {
            textActors.append("$it ")
        }
        Glide
            .with(itemView)
            .load(item.poster)
            .into(imageViewPoster)
    }
}