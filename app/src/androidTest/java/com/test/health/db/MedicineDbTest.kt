package com.test.health.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.health.data.dao.MedicineDao
import com.test.health.data.dao.TypeDao
import com.test.health.data.db.MainDatabase
import com.test.health.data.entities.MedicineAndTypeEntity
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.TypeEntity
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MedicineDbTest {

    private lateinit var typesDao: TypeDao
    private lateinit var medicineDao: MedicineDao
    private lateinit var database: MainDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java).build()
        typesDao = database.typeDao()
        medicineDao = database.medicineDao()
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun test() {
        runBlocking {
            assertEquals(typesDao.checkExists(), 0)

            val typeEntity = TypeEntity(typeId = 1, typeName = "Test", pcsName = "Test")
            val types = listOf(typeEntity)
            typesDao.insertType(typeEntity)
            assertEquals(typesDao.checkExists(), 1)
            assertEquals(types, typesDao.getTypes())

            val medicineEntity = MedicineEntity(medicineId = 1, name = "Test", description = "Test", typeId = 1, dosage = "Test")
            assertEquals(medicineDao.checkExists(), 0)
            medicineDao.insertMedicine(medicineEntity)
            assertEquals(medicineDao.checkExists(), 1)
            assertEquals(medicineDao.getMedicineById(1), medicineEntity)

            medicineEntity.name = "TestTest"
            medicineDao.updateMedicine(medicineEntity)
            assertEquals(medicineDao.getMedicineById(1), medicineEntity)

            val observer = TestableObserver<List<MedicineAndTypeEntity>>()
            withContext(Dispatchers.Main) {
                val data = medicineDao.getMedicine()
                var count = 0
                data.observeForever {
                    if (count < 1) {
                        assertEquals(listOf(MedicineAndTypeEntity(medicineEntity, typeEntity)), it)
                        count++
                    }
                }
            }


            medicineDao.deleteMedicine(medicineEntity)
            assertEquals(medicineDao.checkExists(), 0)

        }
    }
}