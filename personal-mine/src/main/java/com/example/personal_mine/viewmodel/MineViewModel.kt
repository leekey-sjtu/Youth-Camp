package com.example.personal_mine.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.common.base.baseui.BaseViewModel
import com.example.personal_mine.bean.Data
import com.example.personal_mine.repository.MineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MineViewModel : BaseViewModel() {
    private companion object{
        const val TAG="MineViewModel"
    }
    private val _mineLiveData: MutableLiveData<Data> = MutableLiveData()
    val  mMineLiveData :MutableLiveData<Data>
    get() = _mineLiveData

    fun getData() {
        viewModelScope.launch (Dispatchers.Main){
            MineRepository.getMine().collect() {
                Log.e(TAG, "getData: $it", )
                _mineLiveData.postValue(it)
            }
        }
    }

}