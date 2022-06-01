package com.test.health.data.repository

import com.test.health.data.dao.MedicineDao
import com.test.health.data.entities.MedicineEntity
import javax.inject.Inject

class MedicineRepository @Inject constructor(private val medicineDao: MedicineDao) {

    suspend fun getMedicine() = medicineDao.getMedicine()

    suspend fun getMedicineOneShot() = medicineDao.getMedicineSingleShot()

    suspend fun getMedicineById(id: Long) = medicineDao.getMedicineById(id)

    suspend fun updateMedicine(medicineEntity: MedicineEntity) =
        medicineDao.updateMedicine(medicineEntity)

    suspend fun insertMedicine(medicineEntity: MedicineEntity) =
        medicineDao.insertMedicine(medicineEntity)

    suspend fun deleteMedicine(medicineEntity: MedicineEntity) =
        medicineDao.deleteMedicine(medicineEntity)

    suspend fun deleteReceptionWithMedicineId(id: Long) =
        medicineDao.deleteReceptionWithMedicineById(id)

    suspend fun getMedicineCount() = medicineDao.checkExists()
}