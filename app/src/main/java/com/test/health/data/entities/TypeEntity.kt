package com.test.health.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class TypeEntity(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0,
    val typeName: String,
    val pcsName: String
)
