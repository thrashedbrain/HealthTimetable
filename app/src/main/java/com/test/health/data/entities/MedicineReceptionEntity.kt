package com.test.health.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MedicineReceptionEntity(
    @Embedded
    val reception: ReceptionEntity,
    @Relation(entity = MedicineEntity::class, parentColumn = "receptionMedicineId", entityColumn = "medicineId")
    val medicineEntity: MedicineAndTypeEntity,
)
