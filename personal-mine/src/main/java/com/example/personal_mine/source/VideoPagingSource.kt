package com.example.personal_mine.source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.base.constants.TokenConstants
import com.example.personal_mine.bean.MineVideo
import com.example.personal_mine.service.VideoMineService

class VideoPagingSource(
    private val videoMineService: VideoMineService,
    private val openId: String
) :
    PagingSource<Long, MineVideo>() {
    companion object {
        private const val TAG = "VideoPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Long, MineVideo>): Long? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MineVideo> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val videoMineResponse =
                videoMineService.getVideoList(TokenConstants.ACCESS_TOKEN, openId, page, 15)
            val list:ArrayList<MineVideo> = videoMineResponse.data.list as ArrayList<MineVideo>
            list.removeIf{
                it.cover.isBlank()
            }
            val prevKey = if (page > 0) page - 1 else null
            val proxyNextKey = videoMineResponse.data.cursor
            val nextKey =
                if (list.isNotEmpty() && videoMineResponse.data.has_more && params.key != proxyNextKey) videoMineResponse.data.cursor else null
            LoadResult.Page(list, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)

        }
    }
}