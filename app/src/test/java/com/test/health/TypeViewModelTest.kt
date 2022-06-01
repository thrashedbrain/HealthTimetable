package com.test.health

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import com.test.health.data.entities.TypeEntity
import com.test.health.data.repository.TypeRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TypeViewModelTest {

    private val typeRepository = mock<TypeRepository>()

    private lateinit var viewModel: TypeViewModel


    @Before
    fun setup() {
        viewModel = TypeViewModel(InstrumentationRegistry.getInstrumentation().context, typeRepository)
    }

    @Test
    fun `types check`() = runBlocking {

        val data = listOf(TypeEntity(typeName = "test1", pcsName = "type1"), TypeEntity(typeName = "test2", pcsName = "type2"))
        whenever(typeRepository.getTypes()).thenReturn(data)
        Assert.assertEquals("", "")
    }
}