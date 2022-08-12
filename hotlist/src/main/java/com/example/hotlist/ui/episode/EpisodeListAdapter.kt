package com.example.hotlist.ui.episode

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotlist.R
import com.example.hotlist.bean.ListItem

class EpisodeListAdapter(val episodeList: List<ListItem>) : RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun getItemCount(): Int = episodeList.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = episodeList[position]
        holder.bind(item,position + 1)
    }

}

class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var imageViewPoster: ImageView = itemView.findViewById(R.id.imageView_poster)
    private var textName: TextView = itemView.findViewById(R.id.text_name)
    private var textActors: TextView = itemView.findViewById(R.id.text_actors)
    private var textDirectors: TextView = itemView.findViewById(R.id.text_directors)
    private var textTime: TextView = itemView.findViewById(R.id.text_time)
    private var textHot: TextView = itemView.findViewById(R.id.text_hot)
    private var textRanking: TextView = itemView.findViewById(R.id.text_ranking)

    fun bind(item: ListItem, rank: Int) {
        textName.text = item.name

        var actors = ""
        for (i in 0 until (item.actors?.size ?: 0)) {
            actors += if (i == (item.actors?.size ?: 0) - 1) {
                item.actors?.get(i)
            } else {
                item.actors?.get(i) + " / "
            }
        }
        textActors.text = actors

        textTime.text = item.release_date
        textHot.text = String.format("%.2f", (item.discussion_hot?.div(10000.0)))
        Glide
            .with(itemView)
            .load(item.poster)
            .placeholder(R.drawable.placeholder)
            .override(200, 500)
            .into(imageViewPoster)

        var directors = ""
        for (i in 0 until (item.directors?.size ?: 0)){
            directors += if (i == (item.directors?.size ?: 0) - 1) {
                item.directors?.get(i)
            } else {
                item.directors?.get(i) + " / "
            }
        }
        textDirectors.text = directors
        textRanking.visibility = View.VISIBLE
        textRanking.text = if (rank <= 3){
            "TOP${rank}"
        }else if (rank <= 10){
            "${rank}"
        }else {
            textRanking.visibility = View.GONE
            ""
        }
    }
}