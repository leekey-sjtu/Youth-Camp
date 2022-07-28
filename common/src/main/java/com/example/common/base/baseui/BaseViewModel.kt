package com.example.common.base.baseui

import androidx.lifecycle.MutableLiveData
import com.example.common.base.network.LifeViewModel

open class BaseViewModel : LifeViewModel(),IViewModel {

    var loadingEvent = MutableLiveData<Boolean>()

    override fun showLoading() {
        loadingEvent.postValue(true)
    }

    override fun closeLoading() {
        loadingEvent.postValue(false)
    }
}