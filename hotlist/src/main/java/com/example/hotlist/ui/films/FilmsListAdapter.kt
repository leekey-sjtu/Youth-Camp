package com.example.hotlist.ui.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotlist.R
import com.example.hotlist.bean.ListItem

class FilmsListAdapter(val filmsList: List<ListItem>): RecyclerView.Adapter<FilmsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsListViewHolder, position: Int) {
        val film = filmsList[position]
        // 电影名称
        holder.textViewFilmName.text = film.name
        // 电影演员
        var tags = ""
        for (i in 0 until (film.tags?.size ?: 0)) {
            tags += if (i == (film.tags?.size ?: 0) - 1) {
                film.tags?.get(i)
            } else {
                film.tags?.get(i) + " / "
            }
        }
        holder.textViewFilmTags.text = tags
        // 上映时间
        holder.textViewFilmTime.text = film.release_date
        // 热度
        holder.textViewFilmHot.text = String.format("%.2f", (film.discussion_hot?.div(10000.0)))
        // 导演
        var directors = ""
        for (i in 0 until (film.directors?.size ?: 0)){
            directors += if (i == (film.directors?.size ?: 0) - 1) {
                film.directors?.get(i)
            } else {
                film.directors?.get(i) + " / "
            }
        }
        holder.textViewFilmDirector.text = directors
        // 加载图片
        Glide
            .with(holder.imageViewFilmImage)
            .load(film.poster)
            .placeholder(R.drawable.placeholder)
            .override(200, 500)
            .into(holder.imageViewFilmImage)
        val rank = position + 1
        holder.textRanking.visibility = View.VISIBLE
        holder.textRanking.text = if (rank <= 3){
            "TOP${rank}"
        }else if (rank <= 10){
            "${rank}"
        }else {
            holder.textRanking.visibility = View.GONE
            ""
        }
    }

    override fun getItemCount() = filmsList.size
}

class FilmsListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val imageViewFilmImage: ImageView = itemView.findViewById(R.id.imageView_film)
    val textViewFilmName: TextView = itemView.findViewById(R.id.text_filmName)
    val textViewFilmDirector: TextView = itemView.findViewById(R.id.text_director)
    val textViewFilmTags: TextView = itemView.findViewById(R.id.text_tags)
    val textViewFilmTime: TextView = itemView.findViewById(R.id.text_time)
    val textViewFilmHot: TextView = itemView.findViewById(R.id.text_hot)
    val textRanking: TextView = itemView.findViewById(R.id.text_ranking)
}