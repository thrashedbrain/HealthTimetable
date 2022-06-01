package com.test.health.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.health.data.entities.TypeEntity

@Dao
interface TypeDao {

    @Query("select * from types")
    fun getTypes() : List<TypeEntity>

    @Query("select * from types")
    fun getTypesData() : LiveData<List<TypeEntity>>

    @Query("select * from types where typeId = :id")
    suspend fun getType(id: Long) : TypeEntity

    @Query("select count(*) from types")
    suspend fun checkExists(): Int

    @Insert
    suspend fun insertType(typeEntity: TypeEntity)

    @Insert
    suspend fun insertTypes(typeEntities: List<TypeEntity>)

}