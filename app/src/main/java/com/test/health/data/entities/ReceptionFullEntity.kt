package com.test.health.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ReceptionFullEntity(
    @Embedded
    val receptionEntity: ReceptionEntity,
    //@Embedded(prefix = "med_")
    @Embedded
    val medicineEntity: MedicineEntity,
    //@Embedded(prefix = "type_")
    @Embedded
    val typeEntity: TypeEntity
)

fun ReceptionFullEntity.transform(): MedicineReceptionEntity =
    MedicineReceptionEntity(receptionEntity, MedicineAndTypeEntity(medicineEntity, typeEntity))


