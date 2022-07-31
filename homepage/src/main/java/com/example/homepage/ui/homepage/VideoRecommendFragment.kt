package com.example.homepage.ui.homepage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.homepage.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class VideoRecommendFragment : Fragment()  {

    private lateinit var mContext: Context  //获取嵌套的fragment的上下文
    private val viewPager: ViewPager2 by lazy { requireView().findViewById(R.id.viewPager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = requireActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommend_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getVideo()
    }

    private fun getVideo() {
        getRetrofit().create(VideoService::class.java)
            .getVideo("121110910068_portrait")  //获取竖屏视频
            .enqueue(object : Callback<VideoResponse> {
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                    Log.d("wdw", "get Video 3 success")
                    val videoList = response.body()!!.feeds.asReversed()  //获取所有的视频列表
                    viewPager.adapter = VideoRecommendAdapter(mContext,  videoList)
                }
                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    Log.d("wdw", "get Video 3 failed -> $t")
                }
            })
    }

    private fun getRetrofit(): Retrofit {

        val baseUrl = "https://bd-open-lesson.bytedance.com/api/invoke/"
        val client = OkHttpClient.Builder()
            .connectTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间连接服务器
            .readTimeout(15000, TimeUnit.MILLISECONDS)  //预留足够的时间处理数据，否则偶尔出现超时java.net.SocketTimeoutException: timeout
            .build()
        val factory = GsonConverterFactory.create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()

    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE  //切换状态栏字体为白色
    }

}