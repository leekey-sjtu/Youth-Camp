package com.qxy.bitdance.test

import androidx.lifecycle.MutableLiveData
import com.example.common.base.baseui.BaseViewModel

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