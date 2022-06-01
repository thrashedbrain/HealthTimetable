package com.test.health.ui.receptionDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.entities.ReceptionEntity
import com.test.health.data.repository.MedicineRepository
import com.test.health.data.repository.ReceptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReceptionDetailsViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository,
    private val receptionRepository: ReceptionRepository
) : ViewModel() {

    private var date = Date()
    private var medicineId = -1L

    fun setDate(date: Date) {
        this.date = date
    }

    fun getMedicineId() = medicineId

    fun setMedicineId(medicineId: Long) {
        this.medicineId = medicineId
    }

    fun getMedicines() = runBlocking(Dispatchers.IO) { medicineRepository.getMedicineOneShot().plus(
        MedicineEntity(Long.MAX_VALUE, "Добавить", "", -1, "")
    ) }

    fun update(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        val model = getModelById(id).reception
        model.medicineId = medicineId
        model.date = date
        receptionRepository.updateReception(model)
    }

    fun save() = viewModelScope.launch(Dispatchers.IO) {
        if (medicineId != -1L) {
            val receptionEntity = ReceptionEntity(date = date, medicineId = medicineId, done = false)
            receptionRepository.insertReception(receptionEntity)
        }
    }

    fun getModelById(id: Long) = runBlocking(Dispatchers.IO) { receptionRepository.getReceptionById(id) }

}