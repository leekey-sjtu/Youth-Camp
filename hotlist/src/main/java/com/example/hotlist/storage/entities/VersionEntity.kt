package com.example.hotlist.storage.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "version_table", primaryKeys = ["versionId","typeId"])
data class VersionEntity (
    val versionId: Int,
    val typeId: Int,
    val versionName: String,
    val end_time: String,           // 榜单结束时间	2020-03-30 12:00:00
    val start_time: String,         // 榜单开始时间	2020-03-30 12:00:00
)

