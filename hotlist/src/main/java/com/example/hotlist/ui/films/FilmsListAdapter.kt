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
        film.tags.map {
            holder.textViewFilmTags.append(it)
            holder.textViewFilmTags.append(" ")
        }
        // 上映时间
        holder.textViewFilmTime.text = film.release_date
        // 热度
        holder.textViewFilmHot.text = film.discussion_hot.toString()
        // 导演
        film.directors.map {
            holder.textViewFilmDirector.append(it)
            holder.textViewFilmDirector.append(" ")
        }
        // 加载图片
        Glide
            .with(holder.imageViewFilmImage)
            .load(film.poster)
            .into(holder.imageViewFilmImage)
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
}