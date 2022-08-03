package com.example.personal.ui.friendList.fragment.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.personal.bean.Data
import com.example.personal.bean.Friend
import com.example.personal.service.friend.FriendRepository
import kotlinx.coroutines.launch

class FollowViewModel : BaseViewModel() {

    private val followRepository = FriendRepository()
    val followListData = MutableLiveData<List<Friend>>()
    val followData = MutableLiveData<Data>()

    fun getFollowList(cursor: Int){
        viewModelScope.launch {
            showLoading()
            followRepository.getFollowList(cursor).collect{
                followData.value = it
                followListData.postValue(it.list)
                closeLoading()
            }
        }
    }


}