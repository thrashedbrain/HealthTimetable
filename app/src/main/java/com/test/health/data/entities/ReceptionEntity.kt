package com.test.health.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reception")
data class ReceptionEntity(
    @PrimaryKey(autoGenerate = true)
    val receptionId: Long = 0,
    var date: Date,
    @ColumnInfo(name = "receptionMedicineId")
    var medicineId: Long,
    var done: Boolean
)
