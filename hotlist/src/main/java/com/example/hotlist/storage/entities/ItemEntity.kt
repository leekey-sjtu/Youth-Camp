package com.example.hotlist.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity (
    // make rank as itemId
    @PrimaryKey val itemId: Int,
    val versionId: Int,
    val typeId: Int,
    val posterUrl: String? = null,
    val name: String,
    val actors: String? = null,
    val directors: String? = null,
    val discussion_hot: Long? = null,
    val release_date: String? = null
)