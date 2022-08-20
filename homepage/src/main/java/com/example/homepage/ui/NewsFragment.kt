package com.example.homepage.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.common.base.baseui.BaseFragment
import com.example.common.base.utils.MyApplication
import com.example.homepage.BR
import com.example.homepage.R
import com.example.homepage.adapter.NewsAdapter
import com.example.homepage.databinding.FragmentNewsBinding
import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_FAIL
import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_SUCCESS
import com.example.homepage.utils.NetworkUtils.getSkyCondition
import com.example.homepage.viewmodel.NewsViewModel

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>() {

    private lateinit var nowTemperature : TextView
    private lateinit var skyCondition : TextView
    private lateinit var minTemperature : TextView
    private lateinit var maxTemperature : TextView
    private lateinit var tvUpdateTime : TextView
    private lateinit var tvAddConfirm : TextView
    private lateinit var tvAddWzz : TextView
    private lateinit var tvNowConfirm : TextView
    private lateinit var tvNowHeal : TextView
    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutWeather : FrameLayout
    private lateinit var layoutCovid : LinearLayout
    private lateinit var nestedScrollView : NestedScrollView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var handlerSwipeRefresh: Handler
    private var isFirstCreated : Boolean =  true

    override fun getLayoutId() = R.layout.fragment_news
    
    override fun getVariableId() = BR.newsViewModel
    
    override fun initData(savedInstanceState: Bundle?) {
        // 设置 ViewDataBinding
        setViewDataBinding()

        // 设置 SwipeRefreshLayout 下拉刷新
        setSwipeRefreshLayout()

        // 设置 NestedScrollView 滑动监听
        setNestedScrollView()
    }

    private fun setViewDataBinding() {
        nowTemperature = getViewDataBinding().nowTemperature
        skyCondition = getViewDataBinding().skyCondition
        minTemperature = getViewDataBinding().minTemperature
        maxTemperature = getViewDataBinding().maxTemperature
        tvUpdateTime = getViewDataBinding().tvUpdateTime
        tvAddConfirm = getViewDataBinding().tvAddConfirm
        tvAddWzz = getViewDataBinding().tvAddWzz
        tvNowConfirm = getViewDataBinding().tvNowConfirm
        tvNowHeal = getViewDataBinding().tvNowHeal
        recyclerView = getViewDataBinding().recyclerView
        layoutWeather = getViewDataBinding().layoutWeather
        layoutCovid = getViewDataBinding().layoutCovid
        nestedScrollView = getViewDataBinding().nestedScrollView
        swipeRefreshLayout= getViewDataBinding().swipeRefreshLayout
    }

    @SuppressLint("HandlerLeak")
    private fun setSwipeRefreshLayout() {
        handlerSwipeRefresh = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    END_SWIPE_REFRESH_FOR_SUCCESS -> {
                        swipeRefreshLayout.isRefreshing = false
                        layoutWeatherShow(300)
                        layoutCovidShow(300)
                        recyclerViewShow(500)
                        Toast.makeText(MyApplication.context, "刷新成功！", Toast.LENGTH_SHORT).show()
                    }
                    END_SWIPE_REFRESH_FOR_FAIL -> {
                        swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(MyApplication.context, "刷新失败！请重试", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener {  // 开始刷新
            getWeather() // 获取天气情况
            getCovid()   // 获取疫情数据
            getNews()    // 获取头条新闻
        }
    }

    private fun setNestedScrollView() {
        nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            var isFirstHide = true
            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (scrollY == 0) {
                    isFirstHide = true
                    layoutWeatherShow(200)
                    layoutCovidShow(200)
                } else if (isFirstHide) {
                    isFirstHide = false
                    layoutWeatherDisappear(200)
                    layoutCovidDisappear(200)
                }
            }
        })
    }

    private fun getWeather() {
        getViewModel().weatherData.observe(this) {
            nowTemperature.text = it.realtime.temperature.toInt().toString() + "°"
            skyCondition.text = getSkyCondition(it.daily.skycon[0].value)
            maxTemperature.text = it.daily.temperature[0].max.toInt().toString() + "°"
            minTemperature.text = it.daily.temperature[0].min.toInt().toString() + "°"
        }
        getViewModel().getWeather(0)
    }

    private fun getCovid() {
        getViewModel().covidData.observe(this) {
            tvAddConfirm.text = it.diseaseh5Shelf.chinaAdd.confirm.toString()  // 新增确诊
            tvAddWzz.text = it.diseaseh5Shelf.chinaAdd.noInfect.toString()  // 新增无症状
            tvNowConfirm.text = it.diseaseh5Shelf.chinaTotal.nowConfirm.toString()  // 现有确诊
            tvNowHeal.text = it.diseaseh5Shelf.chinaTotal.heal.toString()  // 累计治愈
            tvUpdateTime.text = "更新时间：" + it.diseaseh5Shelf.lastUpdateTime  // 更新时间
        }
        getViewModel().getCovid(0)
    }

    private fun getNews() {
        getViewModel().newsData.observe(this) {
            recyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
            recyclerView.adapter = NewsAdapter(it)
            handlerSwipeRefresh.sendEmptyMessage(END_SWIPE_REFRESH_FOR_SUCCESS)
        }
        getViewModel().getNews(0)
    }

    private fun layoutWeatherShow(duration : Long) {
        layoutWeather.visibility = View.VISIBLE
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 0f, 1f)
        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 0f, 1f)
        animSet.duration = duration
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun layoutWeatherDisappear(duration : Long) {
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 1f, 0f)
        animSet.duration = duration
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun layoutCovidShow(duration : Long) {
        layoutCovid.visibility = View.VISIBLE
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 0f, 1f)
        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 0f, 1f)
        animSet.duration = duration
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun layoutCovidDisappear(duration : Long) {
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 1f, 0f)
        animSet.duration = duration
        animSet.play(animator1).with(animator2).with(animator3)
        animSet.start()
    }

    private fun recyclerViewShow(duration : Long) {
        val animSet = AnimatorSet()
        val animator1 = ObjectAnimator.ofFloat(recyclerView, "alpha", 0f, 1f)
        animSet.duration = duration
        animSet.play(animator1)
        animSet.start()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstCreated) {  // 如果fragment是第一次创建，才加载数据
            swipeRefreshLayout.isRefreshing = true
            getWeather()       // 获取天气情况
            getCovid()         // 获取疫情数据
            getNews()          // 获取头条新闻
            isFirstCreated = false
        }
        requireActivity().window.statusBarColor = Color.parseColor("#87CEEB")
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 状态栏字体黑色
    }

}

//package com.example.homepage.ui
//
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.graphics.Color
//import android.os.Bundle
//import android.os.Handler
//import android.os.Message
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.widget.NestedScrollView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import com.example.common.base.utils.MyApplication
//import com.example.homepage.R
//import com.example.homepage.adapter.NewsAdapter
//import com.example.homepage.bean.CovidResponse
//import com.example.homepage.bean.NewsResponse
//import com.example.homepage.bean.WeatherResponse
//import com.example.homepage.network.getRetrofit
//import com.example.homepage.service.CovidService
//import com.example.homepage.service.NewsService
//import com.example.homepage.service.WeatherService
//import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_FAIL
//import com.example.homepage.ui.HomePageFragment.Companion.END_SWIPE_REFRESH_FOR_SUCCESS
//import com.example.homepage.utils.getSkyCondition
//import com.example.homepage.utils.myLog
//import retrofit2.*
//
//class NewsFragment : Fragment() {
//
//    private val nowTemperature : TextView by lazy { requireView().findViewById(R.id.nowTemperature) }
//    private val skyCondition : TextView by lazy { requireView().findViewById(R.id.skyCondition) }
//    private val minTemperature : TextView by lazy { requireView().findViewById(R.id.minTemperature) }
//    private val maxTemperature : TextView by lazy { requireView().findViewById(R.id.maxTemperature) }
//    private val tvUpdateTime : TextView by lazy { requireView().findViewById(R.id.tv_updateTime) }
//    private val tvAddConfirm : TextView by lazy { requireView().findViewById(R.id.tv_addConfirm) }
//    private val tvAddWzz : TextView by lazy { requireView().findViewById(R.id.tv_addWzz) }
//    private val tvNowConfirm : TextView by lazy { requireView().findViewById(R.id.tv_nowConfirm) }
//    private val tvNowHeal : TextView by lazy { requireView().findViewById(R.id.tv_nowHeal) }
//    private val recyclerView : RecyclerView by lazy { requireView().findViewById(R.id.recyclerView) }
//    private val layoutWeather : FrameLayout by lazy { requireView().findViewById(R.id.layout_weather) }
//    private val layoutCovid : LinearLayout by lazy { requireView().findViewById(R.id.layout_covid) }
//    private val nestedScrollView : NestedScrollView by lazy { requireView().findViewById(R.id.nestedScrollView) }
//    private val swipeRefreshLayout : SwipeRefreshLayout by lazy { requireView().findViewById(R.id.swipeRefreshLayout) }
//    private lateinit var handlerSwipeRefresh: Handler
//    private var isFirstCreated : Boolean =  true
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_news, container, false)
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // 设置 SwipeRefreshLayout 下拉刷新
//        setSwipeRefreshLayout()
//
//        // 设置 NestedScrollView 滑动监听
//        setNestedScrollView()
//    }
//
//
//    private fun getWeather() {
//        getRetrofit("https://api.caiyunapp.com/")
//            .create(WeatherService::class.java)  // TODO： alert = true可以去掉
//            .getDailyWeather("101.6656","39.2072", true, "1", "24")  // TODO：经纬度获取
//            .enqueue(object : Callback<WeatherResponse> {
//                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                    myLog("get daily_weather success")
//                    response.body()?.result?.let {
//                        nowTemperature.text = it.realtime.temperature.toInt().toString() + "°"
//                        skyCondition.text = getSkyCondition(it.daily.skycon[0].value)
//                        maxTemperature.text = it.daily.temperature[0].max.toInt().toString() + "°"
//                        minTemperature.text = it.daily.temperature[0].min.toInt().toString() + "°"
//                    }
//                }
//                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                    myLog("get daily_weather failed --> $t")
//                }
//            })
//    }
//
//
//    private fun getCovid() {
//        getRetrofit("https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/")
//            .create(CovidService::class.java)
//            .getCovid("statisGradeCityDetail,diseaseh5Shelf")
//            .enqueue(object : Callback<CovidResponse> {
//                override fun onResponse(call: Call<CovidResponse>, response: Response<CovidResponse>) {
//                    myLog("get covid_data success")
//                    response.body()?.data?.let {
//                        tvAddConfirm.text = it.diseaseh5Shelf.chinaAdd.confirm.toString()  // 新增确诊
//                        tvAddWzz.text = it.diseaseh5Shelf.chinaAdd.noInfect.toString()  // 新增无症状
//                        tvNowConfirm.text = it.diseaseh5Shelf.chinaTotal.nowConfirm.toString()  // 现有确诊
//                        tvNowHeal.text = it.diseaseh5Shelf.chinaTotal.heal.toString()  // 累计治愈
//                        tvUpdateTime.text = "更新时间：" + it.diseaseh5Shelf.lastUpdateTime  // 更新时间
//                    }
//                }
//                override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
//                    myLog("get covid_data failed -> $t")
//                }
//            })
//    }
//
//
//    private fun getNews() {
//        getRetrofit("https://way.jd.com/jisuapi/")
//            .create(NewsService::class.java)
//            .getNews("头条", "50", "0", "92b9f9e7465ed6a8a72e27330aa8310a") // TODO: appkey需要隐藏
//            .enqueue(object : Callback<NewsResponse> {
//                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                    myLog("get news success")
//                    val newsList = response.body()?.result?.result?.list
//                    recyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
//                    recyclerView.adapter = newsList?.let { NewsAdapter(it) }
//                    handlerSwipeRefresh.sendEmptyMessage(END_SWIPE_REFRESH_FOR_SUCCESS)
//                }
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    myLog("get news failed -> $t")
//                    handlerSwipeRefresh.sendEmptyMessage(END_SWIPE_REFRESH_FOR_FAIL)
//                }
//            })
//    }
//
//
//    private fun setSwipeRefreshLayout() {
//        handlerSwipeRefresh = object : Handler() {
//            override fun handleMessage(msg: Message) {
//                when (msg.what) {
//                    END_SWIPE_REFRESH_FOR_SUCCESS -> {
//                        swipeRefreshLayout.isRefreshing = false
//                        layoutWeatherShow(300)
//                        layoutCovidShow(300)
//                        recyclerViewShow(500)
//                        Toast.makeText(MyApplication.context, "刷新成功！", Toast.LENGTH_SHORT).show()
//                    }
//                    END_SWIPE_REFRESH_FOR_FAIL -> {
//                        swipeRefreshLayout.isRefreshing = false
//                        Toast.makeText(MyApplication.context, "刷新失败！请重试", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE)
//        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
//            override fun onRefresh() {  // 开始刷新
//                getWeather() // 获取天气情况
//                getCovid()   // 获取疫情数据
//                getNews()    // 获取头条新闻
//            }
//        })
//    }
//
//
//    private fun setNestedScrollView() {
//        nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
//            var isFirstHide = true
//            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
//                if (scrollY == 0) {
//                    isFirstHide = true
//                    layoutWeatherShow(200)
//                    layoutCovidShow(200)
//                } else if (isFirstHide) {
//                    isFirstHide = false
//                    layoutWeatherDisappear(200)
//                    layoutCovidDisappear(200)
//                }
//            }
//        })
//    }
//
//
//    private fun layoutWeatherShow(duration : Long) {
//        layoutWeather.visibility = View.VISIBLE
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 0f, 1f)
//        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 0f, 1f)
//        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 0f, 1f)
//        animSet.duration = duration
//        animSet.play(animator1).with(animator2).with(animator3)
//        animSet.start()
//    }
//
//    private fun layoutWeatherDisappear(duration : Long) {
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(layoutWeather, "scaleX", 1f, 0f)
//        val animator2 = ObjectAnimator.ofFloat(layoutWeather, "scaleY", 1f, 0f)
//        val animator3 = ObjectAnimator.ofFloat(layoutWeather, "alpha", 1f, 0f)
//        animSet.duration = duration
//        animSet.play(animator1).with(animator2).with(animator3)
//        animSet.start()
//    }
//
//    private fun layoutCovidShow(duration : Long) {
//        layoutCovid.visibility = View.VISIBLE
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 0f, 1f)
//        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 0f, 1f)
//        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 0f, 1f)
//        animSet.duration = duration
//        animSet.play(animator1).with(animator2).with(animator3)
//        animSet.start()
//    }
//
//    private fun layoutCovidDisappear(duration : Long) {
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(layoutCovid, "scaleX", 1f, 0f)
//        val animator2 = ObjectAnimator.ofFloat(layoutCovid, "scaleY", 1f, 0f)
//        val animator3 = ObjectAnimator.ofFloat(layoutCovid, "alpha", 1f, 0f)
//        animSet.duration = duration
//        animSet.play(animator1).with(animator2).with(animator3)
//        animSet.start()
//    }
//
//
//    private fun recyclerViewShow(duration : Long) {
//        val animSet = AnimatorSet()
//        val animator1 = ObjectAnimator.ofFloat(recyclerView, "alpha", 0f, 1f)
//        animSet.duration = duration
//        animSet.play(animator1)
//        animSet.start()
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        if (isFirstCreated) {  // 如果fragment是第一次创建，才加载数据
//            swipeRefreshLayout.isRefreshing = true
//            getWeather()       // 获取天气情况
//            getCovid()         // 获取疫情数据
//            getNews()          // 获取头条新闻
//            isFirstCreated = false
//        }
//        requireActivity().window.statusBarColor = Color.parseColor("#87CEEB")
//        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 状态栏字体黑色
//    }
//
//
//}