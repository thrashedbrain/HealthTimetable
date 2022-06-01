package com.test.health

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.health.data.entities.TypeEntity
import com.test.health.data.repository.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TypeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val typeRepository: TypeRepository) : ViewModel() {

    fun checkTypes() = viewModelScope.launch(Dispatchers.IO) {
        if (typeRepository.getTypesCount() == 0) {

            val typeList = mutableListOf(
                TypeEntity(1,typeName = context.getString(R.string.type_name1), pcsName =  context.getString(R.string.type_pcs1)),
                TypeEntity(2,typeName = context.getString(R.string.type_name2), pcsName =  context.getString(R.string.type_pcs2)),
                TypeEntity(3,typeName = context.getString(R.string.type_name3), pcsName =  context.getString(R.string.type_pcs3)),
                TypeEntity(4,typeName = context.getString(R.string.type_name4), pcsName =  context.getString(R.string.type_pcs4)),
                TypeEntity(5,typeName = context.getString(R.string.type_name5), pcsName =  context.getString(R.string.type_pcs5)),
                TypeEntity(6,typeName = context.getString(R.string.type_name6), pcsName =  context.getString(R.string.type_pcs6))
            )
            typeRepository.insertTypes(typeList)
        }
    }
}