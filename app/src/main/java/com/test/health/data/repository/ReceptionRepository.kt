package com.test.health.data.repository

import com.test.health.data.dao.ReceptionDao
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.ReceptionEntity
import javax.inject.Inject

class ReceptionRepository @Inject constructor(private val receptionDao: ReceptionDao) {

    suspend fun getReception() = receptionDao.getMedicineAndReception()

    suspend fun getReceptionByName(name: String) = receptionDao.getMedicineAndReceptionByName(name)

    suspend fun getArchiveByDates(firstDay: Long, secondDay: Long) =
        receptionDao.getMedicineAndReceptionArchive(firstDay, secondDay)

    suspend fun updateReception(receptionEntity: ReceptionEntity) =
        receptionDao.updateReception(receptionEntity)

    suspend fun deleteReception(receptionEntity: ReceptionEntity) =
        receptionDao.deleteReception(receptionEntity)

    suspend fun insertReception(receptionEntity: ReceptionEntity) =
        receptionDao.insertReception(receptionEntity)

    suspend fun getReceptionCount() = receptionDao.checkExists()

    suspend fun getReceptionById(id: Long) = receptionDao.getMedicineAndReceptionById(id)
}