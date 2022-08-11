package com.example.personal_mine.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.base.constants.TokenConstants
import com.example.personal_mine.bean.Video
import com.example.personal_mine.service.VideoMineService
import java.lang.Exception

class VideoPagingSource(private val videoMineService: VideoMineService,private val openId:String):
    PagingSource<Int, Video>() {
    companion object{
        private const val TAG = "VideoPagingSource"
    }
    override fun getRefreshKey(state: PagingState<Int, Video>): Int? =null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val page = params.key?:0
            val pageSize = params.loadSize
            Log.e(TAG, "load:$openId ", )
            val videoMineResponse  = videoMineService.getVideoList(TokenConstants.ACCESS_TOKEN,"_000qlpz0TDSfqpA27ggu5qXYOFWfpSk3Xst",1634038666000L,2)
            Log.e(TAG, "load: ${videoMineResponse.data}", )
            val  list= videoMineResponse.data.list
            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (list.isNotEmpty()) page + 1 else null
            LoadResult.Page(list,prevKey,nextKey)
        }catch (e:Exception){
            LoadResult.Error(e)

        }
    }
}