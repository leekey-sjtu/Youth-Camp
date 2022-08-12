package com.example.personal_mine.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.personal_mine.R
import com.example.personal_mine.bean.MineVideo

class RvVideoAdapter : PagingDataAdapter<MineVideo, RvVideoAdapter.ViewHolder>(COMPARATOR) {
    

    companion object {
        private const val TAG = "RvVideoAdapter"
        private val COMPARATOR = object : DiffUtil.ItemCallback<MineVideo>() {
            override fun areItemsTheSame(oldItem: MineVideo, newItem: MineVideo): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: MineVideo, newItem: MineVideo): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        Log.e(TAG, "onBindViewHolder: $video ${video?.title}", )
        if (video!=null&& video.cover.isNotBlank()){
            Log.e(TAG, "onBindViewHolder: ${video.cover} 看看", )
            Glide.with(holder.itemView.context).load(video.cover).override(200,500).into(holder.ivMineVideo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_mine_video, parent, false)
        Log.e(TAG, "onCreateViewHolder: ", )
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMineVideo :ImageView = itemView.findViewById(R.id.iv_mine_video)

    }


}