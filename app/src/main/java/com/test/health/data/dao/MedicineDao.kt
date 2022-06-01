package com.test.health.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.health.data.entities.MedicineAndTypeEntity
import com.test.health.data.entities.MedicineEntity

@Dao
interface MedicineDao {

    @Transaction
    @Query("select * from medecine")
    fun getMedicine(): LiveData<List<MedicineAndTypeEntity>>

    @Transaction
    @Query("select * from medecine")
    fun getMedicineList(): List<MedicineAndTypeEntity>

    @Query("select * from medecine")
    fun getMedicineSingleShot(): List<MedicineEntity>

    @Query("select * from medecine where medicineId = :id ")
    fun getMedicineById(id: Long): MedicineEntity

    @Query("delete from reception where receptionMedicineId = :id ")
    fun deleteReceptionWithMedicineById(id: Long)

    @Insert
    suspend fun insertMedicine(medicineEntity: MedicineEntity)

    @Update
    suspend fun updateMedicine(medicineEntity: MedicineEntity)

    @Delete
    suspend fun deleteMedicine(medicineEntity: MedicineEntity)

    @Query("select count(*) from medecine")
    suspend fun checkExists(): Int

}