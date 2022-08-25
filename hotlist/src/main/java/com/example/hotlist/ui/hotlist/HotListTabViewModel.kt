package com.example.hotlist.ui.hotlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common.base.baseui.BaseViewModel
import com.example.common.base.service.token.TokenRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HotListTabViewModel: BaseViewModel() {
//    private val _accessToken = MutableStateFlow<String>("")
//    // 可观察的数据流，用于获取数据，不能自动刷新token
//    val accessToken: StateFlow<String> = _accessToken
//
//    init {
//        viewModelScope.launch {
//            TokenRepository.getClientToken().collect {
//                _accessToken.value = it
//            }
//        }
//    }

    // 需要刷新 token，可以在协程中订阅这个，其结果为刷新的 token
    val accessTokenStateFlow = TokenRepository.getClientToken().stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = "")
}