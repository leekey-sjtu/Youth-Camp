package com.example.personal.service.friend

import com.example.common.base.constants.TokenConstants
import com.example.common.base.network.RetrofitClient

object FriendNetWork {

    private val friendService = RetrofitClient.retrofit.create(FriendService::class.java)

    suspend fun getFollowList(cursor : Int)
    = friendService.getFollowList(TokenConstants.ACCESS_TOKEN,
        TokenConstants.OPEN_ID,cursor,10)

}