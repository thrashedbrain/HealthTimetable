package com.test.health.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medecine")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val medicineId: Long = 0,
    var name: String,
    val description: String,
    @ColumnInfo(name = "medicineTypeId")
    val typeId: Long,
    val dosage: String
)
