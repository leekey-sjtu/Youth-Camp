package com.example.homepage.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.homepage.bean.CovidData
import com.example.homepage.bean.NewsInfo
import com.example.homepage.bean.WeatherResult
import com.example.homepage.repository.CovidRepository
import com.example.homepage.repository.NewsRepository
import com.example.homepage.repository.WeatherRepository
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel() {

    companion object {
        const val TAG = "NewsViewModel"
    }

    private val weatherRepository = WeatherRepository()
    private val covidRepository = CovidRepository()
    private val newsRepository = NewsRepository()
    val weatherData = MutableLiveData<WeatherResult>()
    val covidData = MutableLiveData<CovidData>()
    val newsData = MutableLiveData<List<NewsInfo>>()

    fun getWeather() {
        viewModelScope.launch {
            weatherRepository.getWeather().collect {
                weatherData.value = it
            }
        }
    }

    fun getCovid() {
       viewModelScope.launch {
            covidRepository.getCovid().collect {
                covidData.value = it
            }
        }
    }

    fun getNews(cursor: Int, handlerSwipeRefresh: Handler) {
        viewModelScope.launch {
            newsRepository.getNews(cursor, handlerSwipeRefresh).collect {
                newsData.value = it
            }
        }
    }

}