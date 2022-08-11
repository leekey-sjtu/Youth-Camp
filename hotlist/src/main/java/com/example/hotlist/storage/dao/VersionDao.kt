package com.example.hotlist.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.hotlist.storage.entities.ItemEntity
import com.example.hotlist.storage.entities.VersionAndItems

@Dao
abstract class VersionDao {
    @Transaction
    @Query("SELECT * FROM version_table WHERE typeId = :typeId AND versionId = :versionId")
    abstract fun getItemsList(typeId: Int, versionId: Int): List<VersionAndItems>

    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract fun insertItemList(itemList: MutableList<ItemEntity>)
}