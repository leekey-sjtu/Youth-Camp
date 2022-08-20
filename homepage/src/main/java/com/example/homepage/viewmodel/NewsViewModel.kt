package com.example.homepage.viewmodel

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

    private val weatherRepository = WeatherRepository()
    private val covidRepository = CovidRepository()
    private val newsRepository = NewsRepository()
    val weatherData = MutableLiveData<WeatherResult>()
    val covidData = MutableLiveData<CovidData>()
    val newsData = MutableLiveData<List<NewsInfo>>()

    fun getWeather(cursor: Int) {
        viewModelScope.launch {
            weatherRepository.getWeather(cursor).collect {
                weatherData.value = it
            }
        }
    }

    fun getCovid(cursor: Int) {
        viewModelScope.launch {
            covidRepository.getCovid(cursor).collect {
                covidData.value = it
            }
        }
    }

    fun getNews(cursor: Int) {
        viewModelScope.launch {
            newsRepository.getNews(cursor).collect {
                newsData.value = it
            }
        }
    }

}