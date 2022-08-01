package com.example.homepage.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homepage.R
import com.example.homepage.bean.Feed

class VideoRecommendAdapter(
    private val context: Context,
    private val videoList: List<Feed>,
): RecyclerView.Adapter<VideoRecommendAdapter.ViewHolder>() {

    private var lastClick = System.currentTimeMillis()  //上次点击视频的时间
    private var curClick = 0L  //这次点击视频的时间

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recommend_video_item_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {  //绑定数据
        val id = videoList[position]._id
        val studentId = videoList[position].student_id
        val userName = videoList[position].user_name
        val extraValue = videoList[position].extra_value
        val videoUrl = videoList[position].video_url
        val imageUrl = videoList[position].image_url
        val imageH = videoList[position].image_h
        val imageW = videoList[position].image_w
        val createdAt = videoList[position].createdAt
        val updatedAt = videoList[position].updatedAt
        val video = holder.videoView
        val videoCover = holder.videoCover
        val progressBar = holder.progressBar

        holder.userName.text = "@$userName"
        holder.extraValue.text = "$extraValue #示例标签"

        Glide.with(context).load(imageUrl).into(videoCover)  //加载视频封面
        video.setVideoURI(Uri.parse(videoUrl))  //设置视频Url
        video.keepScreenOn = true  //屏幕常亮
        video.setOnPreparedListener {  //视频准备完成
            progressBar.visibility = View.GONE  //隐藏加载进度条
            videoCover.visibility = View.GONE  //隐藏视频封面
            video.start()  //自动开始播放
        }
        video.setOnCompletionListener {
            video.start()  //自动重播
        }

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //ViewHolder就是借助他来做到循环利用itemView
        val parentLayout: FrameLayout = itemView.findViewById(R.id.parentLayout)
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
        val videoCover: ImageView = itemView.findViewById(R.id.videoCover)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val imgPlay: ImageView = itemView.findViewById(R.id.imgPlay)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val extraValue: TextView = itemView.findViewById(R.id.extraValue)
        val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        val imgFollow: ImageView = itemView.findViewById(R.id.imgFollow)
        val imgLike: ImageView = itemView.findViewById(R.id.imgLike1)
        val imgLike2: ImageView = itemView.findViewById(R.id.imgLike2)
        val imgComment: ImageView = itemView.findViewById(R.id.imgComment)
        val imgCollect: ImageView = itemView.findViewById(R.id.imgCollect)
        val imgShare: ImageView = itemView.findViewById(R.id.imgShare)
    }
}