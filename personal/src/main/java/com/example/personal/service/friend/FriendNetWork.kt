package com.example.personal.service.friend

import com.example.common.base.network.RetrofitClient

object FriendNetWork {

    private val friendService = RetrofitClient.retrofit.create(FriendService::class.java)

    suspend fun getFollowList(openId : String,cursor : Int) = friendService.getFollowList(openId,cursor,10)

}