package com.example.hotlist.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.hotlist.storage.entities.TypeAndVersions
import com.example.hotlist.storage.entities.TypeEntity
import com.example.hotlist.storage.entities.VersionEntity

@Dao
abstract class TypeDao {
    @Transaction
    @Query("SELECT * FROM type_table WHERE typeId = :typeId")
    abstract fun getVersionList(typeId: Int): MutableList<TypeAndVersions>

    @Transaction
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract fun insertVersionList(versionList: MutableList<VersionEntity>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    abstract fun insertType(type: TypeEntity)

    @Query("SELECT * FROM type_table WHERE typeId = :typeId")
    abstract fun findType(typeId: Int): TypeEntity?

    fun insertVersionListIntoType(typeId: Int,typeName: String, versionList: MutableList<VersionEntity>) {
        var type = findType(typeId)
        if (type == null) {
            type = TypeEntity(typeId, typeName)
            insertType(type)
        }
        insertVersionList(versionList)
    }
}