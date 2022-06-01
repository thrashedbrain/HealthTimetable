package com.test.health.ui.list

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.health.R
import com.test.health.data.entities.MedicineReceptionEntity
import com.test.health.data.models.ReceptionModel
import com.test.health.databinding.ItemClearBinding
import com.test.health.databinding.ItemEmptyBinding
import com.test.health.databinding.ItemReceptionBinding
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder
import java.text.SimpleDateFormat

class ReceptionAdapterProvider {

    fun getAdapter(
        recyclerView: RecyclerView,
        onClearClick: () -> Unit,
        onItemClick: (MedicineReceptionEntity) -> Unit
    ): ListAdapter<ReceptionModel, RecyclerViewHolder<ReceptionModel>> {
        val adapter = adapterOf<ReceptionModel> {
            register(
                layoutResource = R.layout.item_empty,
                viewHolder = ::EmptyHolder,
                onBindViewHolder = { emptyHolder: EmptyHolder, _: Int, _: ReceptionModel.EmptyReception ->
                    emptyHolder.binding.emptyTxt.text =
                        recyclerView.context.getString(R.string.reception_empty_text)

                }
            )
            register(
                layoutResource = R.layout.item_clear,
                viewHolder = ::ClearFilterHolder,
                onBindViewHolder = { clearFilterHolder: ClearFilterHolder, _: Int, _: ReceptionModel.ClearFilter ->
                    clearFilterHolder.binding.root.setOnClickListener {
                        onClearClick()
                    }
                }
            )
            register(
                layoutResource = R.layout.item_reception,
                viewHolder = ::ReceptionHolder,
                onBindViewHolder = { receptionHolder: ReceptionHolder, _: Int, receptionData: ReceptionModel.ReceptionData ->
                    receptionHolder.binding.apply {
                        val format = SimpleDateFormat("HH:mm dd-MM-yyyy")
                        receptionDateTxt.text =
                            format.format(receptionData.medicineReceptionEntity.reception.date)
                        receptionNameTxt.text =
                            receptionData.medicineReceptionEntity.medicineEntity.medicineEntity.name
                        receptionDosageTxt.text =
                            receptionData.medicineReceptionEntity.medicineEntity.medicineEntity.dosage
                        receptionDosageSuffixTxt.text =
                            receptionData.medicineReceptionEntity.medicineEntity.typeEntity.pcsName
                        receptionTypeTxt.text =
                            receptionData.medicineReceptionEntity.medicineEntity.typeEntity.typeName
                        doneContainer.visibility =
                            if (receptionData.medicineReceptionEntity.reception.done) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }
                        root.setOnClickListener { onItemClick(receptionData.medicineReceptionEntity) }
                    }
                }
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        return adapter
    }

    inner class EmptyHolder(view: View) : RecyclerViewHolder<ReceptionModel.EmptyReception>(view) {
        val binding = ItemEmptyBinding.bind(view)
    }

    inner class ClearFilterHolder(view: View) : RecyclerViewHolder<ReceptionModel.ClearFilter>(view) {
        val binding = ItemClearBinding.bind(view)
    }

    inner class ReceptionHolder(view: View) :
        RecyclerViewHolder<ReceptionModel.ReceptionData>(view) {
        val binding = ItemReceptionBinding.bind(view)
    }

}