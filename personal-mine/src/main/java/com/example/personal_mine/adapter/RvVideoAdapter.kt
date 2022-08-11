package com.example.personal_mine.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_mine.R
import com.example.personal_mine.bean.Video

class RvVideoAdapter : PagingDataAdapter<Video, RvVideoAdapter.ViewHolder>(COMPARATOR) {
    

    companion object {
        private const val TAG = "RvVideoAdapter"
        private val COMPARATOR = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        Log.e(TAG, "onBindViewHolder: $video ${video?.title}", )
        if (video!=null){
            holder.tvName.text = video.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_mine_video, parent, false)
        Log.e(TAG, "onCreateViewHolder: ", )
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName :TextView = itemView.findViewById(R.id.test_text)

    }


}