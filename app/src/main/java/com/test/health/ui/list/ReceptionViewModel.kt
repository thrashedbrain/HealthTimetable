package com.test.health.ui.list

import android.util.Log
import androidx.core.util.Pair
import androidx.lifecycle.*
import com.test.health.data.entities.ReceptionEntity
import com.test.health.data.entities.transform
import com.test.health.data.models.MedicineModel
import com.test.health.data.models.ReceptionModel
import com.test.health.data.repository.ReceptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReceptionViewModel @Inject constructor(private val receptionRepository: ReceptionRepository) :
    ViewModel() {

    private val mediatorReceptionData = MediatorLiveData<List<ReceptionModel>>()
    val receptionData: LiveData<List<ReceptionModel>>
        get() = mediatorReceptionData

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        if (receptionRepository.getReceptionCount() > 0) {
            withContext(Dispatchers.Main) {
                mediatorReceptionData.addSource(receptionRepository.getReception().map { list ->
                    list.map { item ->
                        ReceptionModel.ReceptionData(item)
                    }
                }) { mediatorReceptionData.value = it }
            }
        } else {
            withContext(Dispatchers.Main) {
                mediatorReceptionData.value = mutableListOf(ReceptionModel.EmptyReception)
            }
        }
    }

    fun getData(name: String) = viewModelScope.launch(Dispatchers.Main) {
        mediatorReceptionData.addSource(receptionRepository.getReceptionByName(name).map { list ->
            list.map { item ->
                ReceptionModel.ReceptionData(item.transform())
            }
        }) {mediatorReceptionData.value = it}
    }

    fun getArchiveReceptionsByDate(datePair: Pair<Long, Long>) = viewModelScope.launch(Dispatchers.IO) {
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar.time = Date(datePair.first)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar2.time = Date(datePair.second)
        calendar2.set(Calendar.HOUR_OF_DAY, 23)
        calendar2.set(Calendar.MINUTE, 59)
        calendar2.set(Calendar.SECOND, 59)
        val pair = Pair(calendar.time.time, calendar2.time.time)
        val list = runBlocking(Dispatchers.IO) {
            receptionRepository.getArchiveByDates(
                pair.first,
                pair.second
            )
        }

        val secondList = mutableListOf<ReceptionModel>()
            if (list.isNotEmpty()) {
                secondList.add(ReceptionModel.ClearFilter)
                secondList.addAll(list.map {
                    ReceptionModel.ReceptionData(it)
                })
            } else {
                secondList.add(ReceptionModel.ClearFilter)
                secondList.add(ReceptionModel.EmptyReception)
            }
        withContext(Dispatchers.Main) {
            mediatorReceptionData.value = secondList
        }
    }

    fun receptionDone(receptionEntity: ReceptionEntity) = viewModelScope.launch(Dispatchers.IO) {
        receptionEntity.done = true
        receptionRepository.updateReception(receptionEntity)
    }

    fun deleteReception(receptionEntity: ReceptionEntity) = viewModelScope.launch(Dispatchers.IO) {
        receptionRepository.deleteReception(receptionEntity)
    }

}