package com.test.health.data.models

import com.test.health.data.entities.MedicineAndTypeEntity
import com.test.health.data.entities.MedicineEntity

sealed class MedicineModel {

    object EmptyMedicine: MedicineModel()

    class MedicineData(val medicineEntity: MedicineAndTypeEntity): MedicineModel()

}
