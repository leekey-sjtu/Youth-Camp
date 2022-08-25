package com.example.hotlist.service

import android.util.Log
import androidx.room.Room
import com.example.common.base.constants.TokenConstants
import com.example.hotlist.bean.Data
import com.example.hotlist.bean.ListItem
import com.example.hotlist.bean.VData
import com.example.hotlist.bean.VListItem
import com.example.hotlist.storage.entities.ItemEntity
import com.example.hotlist.storage.entities.VersionEntity
import com.example.hotlist.utils.LocalStorageUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object HotListRepository {
    fun getHotList(type: Int, version: Int) = flow {
        val itemDao = LocalStorageUtil.database.itemDao()
        val itemsList = itemDao.getItemsList(typeId = type, versionId = version)
        Log.e("wgw", "getHotListVersion: $itemsList")
        if (itemsList.isNotEmpty()) {
            // 如果本地有数据，就将本地数据打包成需要的格式
            val dataList = mutableListOf<ListItem>()
            for (item in itemsList){
                val actors = item.actors?.trim()?.split("/")
                val directors = item.directors?.trim()?.split("/")
                val listItem = ListItem(actors = actors,name = item.name,directors = directors, poster = item.posterUrl, discussion_hot = item.discussion_hot, release_date = item.release_date)
                dataList.add(listItem)
            }
            val data = Data(list = dataList)
            emit(data)
        }else {
            val hotListResponse = HotListNetWork.getHotList(type, version)
            if (hotListResponse.data.error_code == 0L) {
                // 如果网络请求成功，则插入本地数据库
                val data = hotListResponse.data
                val itemList = mutableListOf<ItemEntity>()
                var i = 1
                for (item in data.list!!){
                    val itemEntity = ItemEntity(itemId = i++, typeId = type, versionId = version, name = item.name!!, posterUrl = item.poster, actors = changeListToString(item.actors), directors = changeListToString(item.directors), release_date = item.release_date, discussion_hot = item.discussion_hot)
                    itemList.add(itemEntity)
                }
                itemDao.insertItemList(itemList)
                emit(hotListResponse.data)
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getHotListVersion(cursor: Long, count: Long, type: Int, getFromNetwork: Boolean) = flow {
        val versionDao = LocalStorageUtil.database.versionDao()
        val localVersionList = versionDao.getVersionList(type)
//        Log.e("wgw", "getHotListVersion: $localVersionList", )
        if (localVersionList.isNotEmpty() && !getFromNetwork){
            // 如果本地有数据，就将本地数据打包成需要的格式
            val vDataList = mutableListOf<VListItem>()
            for (i in 0 until localVersionList.size - 1){
                val localVersion = localVersionList[i]
                val item = VListItem(active_time = localVersion.end_time, start_time = localVersion.start_time, end_time = localVersion.end_time, version = localVersion.versionId, type = type)
                vDataList.add(item)
            }
            val vdata = VData(list = vDataList)
            emit(vdata)
        }else {
            val hotListVersionResponse = HotListNetWork.getHotListVersion(cursor, count, type)
            if (hotListVersionResponse.data.error_code == 0L) {
                // 如果网络请求成功，则插入本地数据库
                val data = hotListVersionResponse.data
                val versionList = mutableListOf<VersionEntity>()
                for (item in data.list) {
                    val versionEntity = VersionEntity(versionId = item.version, typeId = item.type, versionName = item.start_time, end_time = item.end_time, start_time = item.start_time)
                    versionList.add(versionEntity)
                }
                versionDao.insertVersionList(versionList = versionList)
                emit(hotListVersionResponse.data)
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun changeListToString(list: List<String>?): String {
        var sb = ""
        if (list != null) {
            for (i in list.indices){
                sb += if (i == list.size - 1) {
                    list[i]
                } else {
                    list[i] + " / "
                }
            }
        }
        return sb
    }
}