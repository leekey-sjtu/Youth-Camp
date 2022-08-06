package com.example.homepage.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import com.example.common.base.utils.MyApplication
import com.example.homepage.R
import com.example.homepage.adapter.NewsAdapter
import com.example.homepage.bean.CovidBean.COVID19Bean
import com.example.homepage.bean.NewsResponse
import com.example.homepage.service.NewsService
import com.example.homepage.bean.WeatherResponse
import com.example.homepage.network.getRetrofit
import com.example.homepage.service.CovidService
import com.example.homepage.service.WeatherService
import com.example.homepage.utils.getSkyCondition
import com.example.homepage.utils.myLog
import retrofit2.*

class VideoFollowFragment : Fragment() {

    private val nowTemperature : TextView by lazy { requireView().findViewById(R.id.nowTemperature) }
    private val skyCondition : TextView by lazy { requireView().findViewById(R.id.skyCondition) }
    private val minTemperature : TextView by lazy { requireView().findViewById(R.id.minTemperature) }
    private val maxTemperature : TextView by lazy { requireView().findViewById(R.id.maxTemperature) }
    private val tvUpdateTime : TextView by lazy { requireView().findViewById(R.id.tv_updateTime) }
    private val tvAddConfirm : TextView by lazy { requireView().findViewById(R.id.tv_addConfirm) }
    private val tvAddWzz : TextView by lazy { requireView().findViewById(R.id.tv_addWzz) }
    private val tvNowConfirm : TextView by lazy { requireView().findViewById(R.id.tv_nowConfirm) }
    private val tvNowHeal : TextView by lazy { requireView().findViewById(R.id.tv_nowHeal) }
    private val recyclerView : RecyclerView by lazy { requireView().findViewById(R.id.recyclerView) }
    private val layoutWeather : FrameLayout by lazy { requireView().findViewById(R.id.layout_weather) }
    private val layoutCovid : LinearLayout by lazy { requireView().findViewById(R.id.layout_covid) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        return inflater.inflate(R.layout.fragment_video_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDailyWeather()
        getCovidData()
        getNews()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isFirstHide = true
            var isFirstShow = true
            var lastTime = System.currentTimeMillis()
            var currentTime = 0L
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 5 && isFirstHide) {
                    currentTime = System.currentTimeMillis()
                    if (currentTime - lastTime > 30) {
                        isFirstHide = false
                        isFirstShow = true
                        layoutWeatherDisappear()
                        layoutCovidDisappear()
                    }
                    lastTime = currentTime
                }
                if (dy < -20 && isFirstShow) {
                    currentTime = System.currentTimeMillis()
                    if (currentTime - lastTime > 30) {
                        isFirstHide = true
                        isFirstShow = false
                        layoutWeatherShow()
                        layoutCovidShow()
                    }
                    lastTime = currentTime
                }
            }
        })

    }


    private fun getDailyWeather() {
        getRetrofit("https://api.caiyunapp.com/")
            .create(WeatherService::class.java)  // TODO： alert = true可以去掉
            .getDailyWeather("101.6656","39.2072", true, "1", "24")  // TODO：经纬度
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>, ) {
                    myLog("get daily_weather success")
                    response.body()?.result?.let {
                        nowTemperature.text = it.realtime.temperature.toInt().toString() + "°"
                        skyCondition.text = getSkyCondition(it.daily.skycon[0].value)
                        maxTemperature.text = it.daily.temperature[0].max.toInt().toString() + "°"
                        minTemperature.text = it.daily.temperature[0].min.toInt().toString() + "°"
                    }
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    myLog("get daily_weather failed --> $t")
                }
            })
    }


    private fun getCovidData() {
        getRetrofit("https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/")
            .create(CovidService::class.java)
            .getData("statisGradeCityDetail,diseaseh5Shelf")
            .enqueue(object : Callback<COVID19Bean> {
                override fun onResponse(call: Call<COVID19Bean>, response: Response<COVID19Bean>) {
                    Log.d("wdw", "get covid_data success")
                    response.body()?.data?.let {
                        tvAddConfirm.text = it.diseaseh5Shelf.chinaAdd.confirm.toString()  // 新增确诊
                        tvAddWzz.text = it.diseaseh5Shelf.chinaAdd.noInfect.toString()  // 新增无症状
                        tvNowConfirm.text = it.diseaseh5Shelf.chinaTotal.nowConfirm.toString()  // 现有确诊
                        tvNowHeal.text = it.diseaseh5Shelf.chinaTotal.heal.toString()  // 累计治愈
                        tvUpdateTime.text = "更新时间：" + it.diseaseh5Shelf.lastUpdateTime  // 更新时间
                    }
                }
                override fun onFailure(call: Call<COVID19Bean>, t: Throwable) {
                    Log.d("wdw", "get covid_data failed -> $t")
                }
            })
    }


    private fun getNews() {
        getRetrofit("https://way.jd.com/jisuapi/")
            .create(NewsService::class.java)
            .getJDNews("头条", "50", "0", "92b9f9e7465ed6a8a72e27330aa8310a") // TODO: appkey需要隐藏
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    Log.d("wdw", "get news success")
                    val newsList = response.body()?.result?.result?.list
                    recyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
                    recyclerView.adapter = newsList?.let { NewsAdapter(it) }
                }
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d("wdw", "get news failed, $t")
                }
            })
    }


    private fun layoutWeatherShow() {
        layoutWeather.visibility = View.VISIBLE
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 0f, 1f)
        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 0f, 1f)
        animSet.duration = 200
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun layoutWeatherDisappear() {
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 1f, 0f)
        animSet.duration = 100
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
        layoutWeather.visibility = View.GONE
    }

    private fun layoutCovidShow() {
        layoutCovid.visibility = View.VISIBLE
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 0f, 1f)
        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 0f, 1f)
        animSet.duration = 200
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun layoutCovidDisappear() {
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 1f, 0f)
        animSet.duration = 100
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
        layoutCovid.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        myLog("follow onResume")
        requireActivity().window.statusBarColor = Color.parseColor("#87CEEB")
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 状态栏字体黑色
    }

}