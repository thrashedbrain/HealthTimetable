package com.test.health.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.MedicineReceptionEntity
import com.test.health.data.entities.ReceptionEntity
import com.test.health.data.entities.ReceptionFullEntity

@Dao
interface ReceptionDao {

    @Transaction
    @Query("select * from reception order by date")
    fun getMedicineAndReception(): LiveData<List<MedicineReceptionEntity>>

    //@Transaction
    @Query("select * from reception inner join medecine on receptionMedicineId = medicineId inner join types on medicineTypeId = typeId where name like '%' || :name || '%'")
    fun getMedicineAndReceptionByName(name: String): LiveData<List<ReceptionFullEntity>>

    @Transaction
    @Query("select * from reception where date between :dayFirst AND :daySecond order by date")
    fun getMedicineAndReceptionArchive(dayFirst: Long, daySecond: Long): List<MedicineReceptionEntity>

    @Transaction
    @Query("select * from reception where receptionId = :receptionId")
    fun getMedicineAndReceptionById(receptionId: Long): MedicineReceptionEntity

    @Transaction
    @Query("select * from reception")
    fun getMedicineAndReceptionOneShot(): List<MedicineReceptionEntity>

    @Query("select * from reception")
    fun getReceptionOneShot(): List<ReceptionEntity>


    @Query("select count(*) from reception")
    suspend fun checkExists(): Int

    @Insert
    suspend fun insertReception(receptionEntity: ReceptionEntity)

    @Update
    suspend fun updateReception(receptionEntity: ReceptionEntity)

    @Delete
    suspend fun deleteReception(receptionEntity: ReceptionEntity)
}