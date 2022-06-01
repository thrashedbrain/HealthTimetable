package com.test.health.data.models

import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.MedicineReceptionEntity

sealed class ReceptionModel {

    object EmptyReception: ReceptionModel()

    object ClearFilter: ReceptionModel()

    class ReceptionData(val medicineReceptionEntity: MedicineReceptionEntity): ReceptionModel()

}
