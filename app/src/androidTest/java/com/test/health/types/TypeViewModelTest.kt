package com.test.health.types

import android.app.Instrumentation
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.test.health.R
import com.test.health.TypeViewModel
import com.test.health.data.dao.TypeDao
import com.test.health.data.db.MainDatabase
import com.test.health.data.entities.TypeEntity
import com.test.health.data.repository.TypeRepository
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TypeViewModelTest {

    private lateinit var viewModel: TypeViewModel
    private lateinit var typesDao: TypeDao
    private lateinit var database: MainDatabase
    private lateinit var typeRepository: TypeRepository
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java).build()
        typesDao = database.typeDao()
        typeRepository = TypeRepository(typesDao)
        viewModel = TypeViewModel(InstrumentationRegistry.getInstrumentation().context, typeRepository)
    }

    @Test
    fun test() = runBlocking {
        viewModel.checkTypes()
        val typeList = mutableListOf(
            TypeEntity(typeName = context.getString(R.string.type_name1), pcsName =  context.getString(
                R.string.type_pcs1)),
            TypeEntity(typeName = context.getString(R.string.type_name2), pcsName =  context.getString(
                R.string.type_pcs2)),
            TypeEntity(typeName = context.getString(R.string.type_name3), pcsName =  context.getString(
                R.string.type_pcs3)),
            TypeEntity(typeName = context.getString(R.string.type_name4), pcsName =  context.getString(
                R.string.type_pcs4)),
            TypeEntity(typeName = context.getString(R.string.type_name5), pcsName =  context.getString(
                R.string.type_pcs5)),
            TypeEntity(typeName = context.getString(R.string.type_name6), pcsName =  context.getString(
                R.string.type_pcs6))
        )

        val list = typesDao.getTypes()
        Assert.assertEquals(typeList, list)
    }

}