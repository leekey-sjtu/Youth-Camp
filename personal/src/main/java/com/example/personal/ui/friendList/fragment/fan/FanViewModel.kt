package com.example.personal.ui.friendList.fragment.fan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.personal.bean.Data
import com.example.personal.bean.Friend
import com.example.personal.service.friend.FriendRepository
import kotlinx.coroutines.launch

class FanViewModel : BaseViewModel() {

    private val followRepository = FriendRepository()
    val fansListData = MutableLiveData<List<Friend>>()
    val fansData = MutableLiveData<Data>()

    fun getFansList(cursor: Int){
        viewModelScope.launch {
            showLoading()
            followRepository.getFansList(cursor).collect{
                fansData.value = it
                fansListData.postValue(it.list)
                closeLoading()
            }
        }
    }

}