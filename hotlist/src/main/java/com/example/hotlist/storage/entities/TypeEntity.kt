package com.example.hotlist.storage.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "type_table")
data class TypeEntity (
    @PrimaryKey val typeId: Int,
    val typeName: String
)

@Entity
data class TypeAndVersions(
    @Embedded val type: TypeEntity,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "typeId"
    )
    val versions: MutableList<VersionEntity>
)