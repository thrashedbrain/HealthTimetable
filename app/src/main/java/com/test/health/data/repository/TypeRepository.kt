package com.test.health.data.repository

import com.test.health.data.dao.TypeDao
import com.test.health.data.entities.TypeEntity
import javax.inject.Inject

class TypeRepository @Inject constructor(private val typeDao: TypeDao) {

    suspend fun getTypes() = typeDao.getTypes()

    suspend fun getType(id: Long) = typeDao.getType(id)

    suspend fun getTypesCount() = typeDao.checkExists()

    suspend fun insertType(typeEntity: TypeEntity) = typeDao.insertType(typeEntity)

    suspend fun insertTypes(typeEntities: List<TypeEntity>) = typeDao.insertTypes(typeEntities)
}