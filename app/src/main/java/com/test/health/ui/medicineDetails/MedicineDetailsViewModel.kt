package com.test.health.ui.medicineDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.repository.MedicineRepository
import com.test.health.data.repository.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MedicineDetailsViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository,
    private val typeRepository: TypeRepository
) : ViewModel() {

    private var typeId: Long = -1L

    private val stateMutableLiveData = MutableLiveData<String>()
    val stateLiveData: LiveData<String>
        get() = stateMutableLiveData

    fun setType(type: Long) {
        this.typeId = type
    }

    fun getType() = typeId

    fun getMedicine(id: Long): MedicineEntity = runBlocking(Dispatchers.IO) {
        medicineRepository.getMedicineById(id)
    }

    fun saveMedicine(medicineEntity: MedicineEntity) = viewModelScope.launch(Dispatchers.IO) {
        medicineRepository.insertMedicine(medicineEntity)
    }

    fun updateMedicine(medicineEntity: MedicineEntity) = viewModelScope.launch(Dispatchers.IO) {
        medicineRepository.updateMedicine(medicineEntity)
    }

    fun getTypeById(id: Long) = runBlocking(Dispatchers.IO) {typeRepository.getType(id)}

    fun getTypes() = runBlocking(Dispatchers.IO) { typeRepository.getTypes() }


}