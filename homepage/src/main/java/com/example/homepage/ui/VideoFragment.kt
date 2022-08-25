package com.example.homepage.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.baseui.BaseFragment
import com.example.common.base.utils.MyApplication
import com.example.homepage.BR
import com.example.homepage.R
import com.example.homepage.adapter.VideoAdapter
import com.example.homepage.bean.Feed
import com.example.homepage.databinding.FragmentVideoBinding
import com.example.homepage.viewmodel.VideoViewModel

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {

    companion object {
        const val TAG = "VideoFragment"
    }

    lateinit var viewPager: ViewPager2
    private lateinit var videoList: List<Feed>
    private var isFirstCreated : Boolean =  true

    override fun getLayoutId() = R.layout.fragment_video

    override fun getVariableId() = BR.videoViewModel

    override fun initData(savedInstanceState: Bundle?) {
        // 设置 ViewDataBinding
        setViewDataBinding()

        // 设置 ViewPager
        setViewPager()

        // 获取新闻
        getVideo()
    }

    private fun setViewDataBinding() {
        viewPager = getViewDataBinding().viewPager
    }

    private fun setViewPager() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val recyclerView = viewPager.getChildAt(0) as RecyclerView
                val view = recyclerView.layoutManager?.findViewByPosition(position)!!
                val imgPlay = view.findViewById<ImageView>(R.id.imgPlay)!!
                imgPlay.visibility = View.GONE
                val videoView = view.findViewById<VideoView>(R.id.videoView)!!
//                val proxy = MyApplication.getProxy(MyApplication.context)
//                val videoUrl = videoList[position].video_url
//                val proxyUrl = proxy.getProxyUrl(videoUrl)
//                videoView.setVideoPath(proxyUrl)
//                if (proxy.isCached(videoUrl)) Log.e(TAG, "position $position selected, 已缓存")else Log.e(TAG, "position $position selected, 未缓存")
            }
        })
        viewPager.setPageTransformer { page, position ->  }  // TODO 设置 viewPager 透明过渡动画
    }

    private fun getVideo() {
        getViewModel().videoListData.observe(this) {
            videoList = it
            viewPager.adapter = VideoAdapter(it)
        }
        getViewModel().getVideoList(0)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.BLACK //设置状态栏颜色
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE //实现状态栏字体白色
        if (isFirstCreated) {  // 如果fragment是第一次创建，才加载数据
            getVideo()
            isFirstCreated = false
        }
        val recyclerView= viewPager.getChildAt(0) as RecyclerView
        val view = recyclerView.layoutManager?.findViewByPosition(viewPager.currentItem)
        val videoView = view?.findViewById<VideoView>(R.id.videoView)
        if (videoView?.isPlaying == false && videoView.isShown) {
            videoView.start()
        }
        val imgPlay = view?.findViewById<ImageView>(R.id.imgPlay)
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(imgPlay, "scaleX", 1f, 2f)
        val animator2 = ObjectAnimator.ofFloat(imgPlay, "scaleY", 1f, 2f)
        val animator3 = ObjectAnimator.ofFloat(imgPlay, "alpha", 0.2f, 0f)
        animSet.duration = 250
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

}

//class VideoFragment : Fragment()  {
//
//    private var videoList: List<Feed>? = null
//    private var isFirstCreated : Boolean =  true
//    private lateinit var viewModel: VideoViewModel
//    val viewPager: ViewPager2 by lazy { requireView().findViewById(R.id.viewPager) }
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_recommend_video, container, false)
//    }
//
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                val recyclerView = viewPager.getChildAt(0) as RecyclerView
//                val view = recyclerView.layoutManager?.findViewByPosition(position)!!
//                val imgPlay = view.findViewById<ImageView>(R.id.imgPlay)!!
//                imgPlay.visibility = View.GONE
//                val videoView = view.findViewById<VideoView>(R.id.videoView)!!
//                val proxy = MyApplication.getProxy(MyApplication.context)
//                val videoUrl = videoList?.get(position)?.video_url
//                val proxyUrl = proxy.getProxyUrl(videoUrl)
//                videoView.setVideoPath(proxyUrl)
//                if (proxy.isCached(videoUrl)) myLog("position $position selected, 已缓存") else myLog("position $position selected, 未缓存")
//            }
////        viewPager.setPageTransformer { page, position ->  }  // TODO 设置 viewPager 透明过渡动画
//        })
//    }
//
//    override fun onResume() {
//        super.onResume()
//        requireActivity().window.statusBarColor = Color.BLACK //设置状态栏颜色
//        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE //实现状态栏字体白色
//        if (isFirstCreated) {  // 如果fragment是第一次创建，才加载数据
//            getVideo()
//            isFirstCreated = false
//        }
//        val recyclerView= viewPager.getChildAt(0) as RecyclerView
//        val view = recyclerView.layoutManager?.findViewByPosition(viewPager.currentItem)
////        val videoView = view?.findViewById<VideoView>(R.id.videoView)
////        if (videoView?.isPlaying == false) {
////            videoView.start()
////        }
//        val imgPlay = view?.findViewById<ImageView>(R.id.imgPlay)
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(imgPlay, "scaleX", 1f, 2f)
//        val animator2 = ObjectAnimator.ofFloat(imgPlay, "scaleY", 1f, 2f)
//        val animator3 = ObjectAnimator.ofFloat(imgPlay, "alpha", 0.2f, 0f)
//        animSet.duration = 250
//        animSet.play(animator1).with(animator2).with(animator3)
//        animSet.start()
//    }
//
//
//    private fun getVideo() {
//        getRetrofit().create(VideoService::class.java)
//            .getVideo("121110910068_portrait")  //获取竖屏视频
//            .enqueue(object : Callback<VideoResponse> {
//                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
//                    myLog("get recommend_video success")
//                    videoList = response.body()?.feeds?.asReversed()  //获取所有的视频列表
//                    viewPager.adapter = videoList?.let { VideoAdapter(it) }
//                }
//                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
//                    myLog("get recommend_video failed -> $t")
//                }
//            })
//    }
//
//
//    private fun getRetrofit(): Retrofit {
//        val baseUrl = "https://bd-open-lesson.bytedance.com/api/invoke/"
//        val client = OkHttpClient.Builder()
//            .connectTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间连接服务器
//            .readTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间处理数据，否则偶尔出现超时java.net.SocketTimeoutException: timeout
//            .build()
//        val factory = GsonConverterFactory.create()
//
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .client(client)
//            .addConverterFactory(factory)
//            .build()
//    }
//
//}