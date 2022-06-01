package com.test.health.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MedicineAndTypeEntity(
    @Embedded val medicineEntity: MedicineEntity,
    @Relation(parentColumn = "medicineTypeId", entityColumn = "typeId")
    val typeEntity: TypeEntity
)
