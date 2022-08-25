package com.example.hotlist.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hotlist.storage.entities.VersionEntity

@Dao
interface VersionDao {
    @Query("SELECT * FROM version_table WHERE typeId = :typeId")
    fun getVersionList(typeId: Int): List<VersionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersionList(versionList: MutableList<VersionEntity>)
}