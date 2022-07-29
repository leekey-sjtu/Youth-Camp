package com.qxy.bitdance.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    private companion object{
        const val  TAG = "MainViewModel"
    }
    private val mRepository = CatListRepository()
    val catListData = MutableLiveData<List<Sub>>()

//    fun getCatList(){
//         viewModelScope.launch {
//             mRepository.getCatList().collect{
//                 Log.e(TAG, "getCatList: 看看这个it $it")
//                 catListData.postValue(it)
//             }
//         }
//    }
}