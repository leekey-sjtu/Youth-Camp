package com.example.hotlist.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.hotlist.storage.entities.ItemEntity

@Dao
interface ItemDao {
    @Transaction
    @Query("SELECT * FROM items_table WHERE typeId = :typeId AND versionId = :versionId")
    fun getItemsList(typeId: Int, versionId: Int): List<ItemEntity>

    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertItemList(itemList: MutableList<ItemEntity>)
}