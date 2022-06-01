package com.test.health.ui.medicine

import android.util.Log
import androidx.lifecycle.*
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.models.MedicineModel
import com.test.health.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(private val medicineRepository: MedicineRepository) :
    ViewModel() {

    fun getData() = liveData {
        if (medicineRepository.getMedicineCount() > 0) {
            emitSource(medicineRepository.getMedicine().map { list ->
                list.map { item ->
                    MedicineModel.MedicineData(item)
                }
            })
        } else {
            emit(mutableListOf(MedicineModel.EmptyMedicine))
        }
    }

    fun deleteMedicine(medicineEntity: MedicineEntity) = viewModelScope.launch(Dispatchers.IO) {
        medicineRepository.deleteReceptionWithMedicineId(medicineEntity.medicineId)
        medicineRepository.deleteMedicine(medicineEntity)
    }

}