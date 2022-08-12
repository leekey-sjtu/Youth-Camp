package com.example.hotlist.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hotlist.storage.dao.TypeDao
import com.example.hotlist.storage.dao.VersionDao
import com.example.hotlist.storage.entities.ItemEntity
import com.example.hotlist.storage.entities.TypeEntity
import com.example.hotlist.storage.entities.VersionEntity

@Database(entities = [TypeEntity::class,VersionEntity::class,ItemEntity::class], version = 1,exportSchema = false)
abstract class HotListDatabase : RoomDatabase() {
    abstract fun typeDao(): TypeDao
    abstract fun versionDao(): VersionDao
}