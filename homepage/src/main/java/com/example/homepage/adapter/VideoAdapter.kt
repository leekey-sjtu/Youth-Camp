package com.example.homepage.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.base.utils.MyApplication
import com.example.homepage.R
import com.example.homepage.bean.Feed
import com.example.homepage.utils.myLog
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class VideoAdapter(private val videoList: List<Feed>): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val handler = Handler(Looper.getMainLooper())
    private var lastClick = System.currentTimeMillis()  //上次点击视频的时间
    private var curClick = 0L  //这次点击视频的时间

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recommend_video_item_view, parent, false)
        return ViewHolder(view)
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


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {  //绑定数据
//        myLog("onBindViewHolder  position = $position")
        playAnimator(holder)
        val id = videoList[position]._id
        val studentId = videoList[position].student_id
        val userName = videoList[position].user_name
        val extraValue = videoList[position].extra_value
        val imageH = videoList[position].image_h
        val imageW = videoList[position].image_w
        val createdAt = videoList[position].createdAt
        val updatedAt = videoList[position].updatedAt
        setVideoView(holder, position)  // 设置VideoView
        holder.userName.text = "@$userName"
        holder.extraValue.text = extraValue

        holder.imgLike.setOnClickListener {  //点赞
            imgLikeAnimator(holder)
        }
        holder.imgComment.setOnClickListener {  //评论区
            //TODO
        }
        holder.imgCollect.setOnClickListener {  //收藏
            //TODO
        }
        holder.imgShare.setOnClickListener {  //分享
            //TODO
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setVideoView(holder: ViewHolder, position: Int) {
        myLog("setVideoView  position = $position")
        val videoView = holder.videoView
        val videoCover = holder.videoCover
        val progressBar = holder.progressBar
        val videoUrl = videoList[position].video_url
        val imageUrl = videoList[position].image_url
        Glide.with(MyApplication.context).load(imageUrl).into(videoCover)  //加载视频封面
        videoView.setVideoURI(Uri.parse(videoUrl))  //设置视频Url
        videoView.keepScreenOn = true  //屏幕常亮
//        videoView.setMediaController(MediaController(context))  // 设置内置控制器
        videoView.setOnPreparedListener {  //视频准备完成
            progressBar.visibility = View.GONE  //隐藏加载进度条
            videoCover.visibility = View.GONE  //隐藏视频封面
            videoView.start()  //自动开始播放
        }
        videoView.setOnCompletionListener {
            videoView.start()  //自动重播
        }
        val runnable: Runnable = Runnable {  //单击的runnable
            run {
                playAnimator(holder)  //单击视频，播放&暂停动画
                if (videoView.isPlaying) videoView.pause() else videoView.start()
            }
        }
        videoView.setOnTouchListener { view: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    curClick = System.currentTimeMillis()
                    if (curClick - lastClick < 200) {  //双击视频, 连击视频
                        Log.d("wdw", "click间隔${curClick-lastClick}ms")
                        handler.removeCallbacks(runnable)  //双击，拦截单击事件
                        if (holder.imgLike.scaleX == 1f) { imgLikeAnimator(holder) }  //双击播放点赞动画
                        imgLoveAnimator(event.x, event.y, holder)//同时播放屏幕点赞动画
                    } else {
                        handler.postDelayed(runnable, 200)  //延迟ms执行单击事件
                    }
                    lastClick = curClick
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                }
            }
            true
        }
    }


    private fun imgLoveAnimator(x: Float, y: Float, holder: ViewHolder) {
        val deg = Random.nextInt(-30, 30).toFloat()  //生成-30 ~ 30随机度数
        val imgLove = ImageView(MyApplication.context)
        holder.parentLayout.addView(imgLove)  //添加imgLove
        imgLove.setImageResource(R.drawable.ic_love_2)
        imgLove.layoutParams.width = 300
        imgLove.layoutParams.height = 300
        imgLove.x = x - 150  //设置imgLove的横纵坐标
        imgLove.y = y + 100
        imgLove.rotation = deg  //设置imgLove的旋转角
        val animator1 = ObjectAnimator.ofFloat(imgLove, "scaleX", 1.25f, 0.75f, 1f)
        val animator2 = ObjectAnimator.ofFloat(imgLove, "scaleY", 1.25f, 0.75f, 1f)
        val animator3 = ObjectAnimator.ofFloat(imgLove, "alpha", 1f, 1f)
        val animator4 = ObjectAnimator.ofFloat(imgLove, "scaleX", 1f, 2f)
        val animator5 = ObjectAnimator.ofFloat(imgLove, "scaleY", 1f, 2f)
        val animator6 = ObjectAnimator.ofFloat(imgLove, "alpha", 1f, 0f)
        val animator7 = ObjectAnimator.ofFloat(imgLove, "translationX", imgLove.x, imgLove.x + 100 * sin(deg * PI / 180).toFloat())
        val animator8 = ObjectAnimator.ofFloat(imgLove, "translationY", imgLove.y, imgLove.y - 100 * cos(deg * PI / 180).toFloat())
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()  //播放imgLove的动画
        handler.postDelayed({
            val animSet2 = AnimatorSet()
            animSet2.duration = 300
            animSet2.play(animator4).with(animator5).with(animator6).with(animator7).with(animator8)
            animSet2.start()
        }, 700)  //700ms后播放imgLove消失动画
    }

    private fun imgLikeAnimator(holder: ViewHolder) {
        val imgLike = holder.imgLike
        val imgLike2 = holder.imgLike2
        if (imgLike.scaleX == 1.0f) {
            val animator1 = ObjectAnimator.ofFloat(imgLike, "scaleX", 1f, 0f)
            val animator2 = ObjectAnimator.ofFloat(imgLike, "scaleY", 1f, 0f)
//            val animator3 = ObjectAnimator.ofFloat(imgLike2, "alpha", 1f)
            val animator4 = ObjectAnimator.ofFloat(imgLike2, "scaleX", 0f, 1.25f, 1f)
            val animator5 = ObjectAnimator.ofFloat(imgLike2, "scaleY", 0f, 1.25f, 1f)
            val animSet = AnimatorSet()
            animSet.duration = 150
            animSet.play(animator4).with(animator5).after(animator1).after(animator2)
            animSet.start()
        } else {
            val animator1 = ObjectAnimator.ofFloat(imgLike, "scaleX", 0.8f, 1f)
            val animator2 = ObjectAnimator.ofFloat(imgLike, "scaleY", 0.8f, 1f)
            val animator3 = ObjectAnimator.ofFloat(imgLike2, "scaleX", 1f, 0f)
            val animator4 = ObjectAnimator.ofFloat(imgLike2, "scaleY", 1f, 0f)
            val animSet = AnimatorSet()
            animSet.duration = 150
            animSet.play(animator1).with(animator2).with(animator3).with(animator4)
            animSet.start()
        }

    }

    private fun playAnimator(holder: ViewHolder) {
        val imgPlay = holder.imgPlay
        imgPlay.visibility = View.VISIBLE
        val animator1: ObjectAnimator
        val animator2: ObjectAnimator
        val animator3: ObjectAnimator
        val animSet = AnimatorSet()
        if (holder.videoView.isPlaying) {  //暂停的动画
            animator1 = ObjectAnimator.ofFloat(imgPlay, "scaleX", 2f, 1f)
            animator2 = ObjectAnimator.ofFloat(imgPlay, "scaleY", 2f, 1f)
            animator3 = ObjectAnimator.ofFloat(imgPlay, "alpha", 0f, 0.2f)
        } else {  //播放的动画
            animator1 = ObjectAnimator.ofFloat(imgPlay, "scaleX", 1f, 2f)
            animator2 = ObjectAnimator.ofFloat(imgPlay, "scaleY", 1f, 2f)
            animator3 = ObjectAnimator.ofFloat(imgPlay, "alpha", 0.2f, 0f)
        }
        animSet.duration = 250
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

}