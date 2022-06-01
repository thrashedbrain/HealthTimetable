package com.test.health.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.health.data.dao.MedicineDao
import com.test.health.data.dao.ReceptionDao
import com.test.health.data.dao.TypeDao
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.ReceptionEntity
import com.test.health.data.entities.TypeEntity

@Database(
    entities = [MedicineEntity::class, ReceptionEntity::class, TypeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun medicineDao(): MedicineDao

    abstract fun receptionDao(): ReceptionDao

    abstract fun typeDao(): TypeDao
}