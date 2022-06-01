package com.test.health.ui.medicine

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.health.R
import com.test.health.data.entities.MedicineEntity
import com.test.health.data.models.MedicineModel
import com.test.health.databinding.ItemEmptyBinding
import com.test.health.databinding.ItemMedicineBinding
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class MedicineAdapterProvider {

    fun getAdapter(
        recyclerView: RecyclerView,
        onItemClick: (MedicineEntity) -> Unit
    ): ListAdapter<MedicineModel, RecyclerViewHolder<MedicineModel>> {
        val adapter = adapterOf<MedicineModel> {
            register(
                layoutResource = R.layout.item_empty,
                viewHolder = ::EmptyHolder,
                onBindViewHolder = { emptyHolder: EmptyHolder, _: Int, _: MedicineModel.EmptyMedicine ->
                    emptyHolder.binding.emptyTxt.text = recyclerView.context.getString(R.string.medicine_empty_text)
                }
            )
            register(
                layoutResource = R.layout.item_medicine,
                viewHolder = ::MedicineHolder,
                onBindViewHolder = { medicineHolder: MedicineHolder, _: Int, medicineData: MedicineModel.MedicineData ->
                    medicineHolder.binding.apply {
                        medicineNameTxt.text = medicineData.medicineEntity.medicineEntity.name
                        medicineDescTxt.text = medicineData.medicineEntity.medicineEntity.description
                        medicineDosageTxt.text = medicineData.medicineEntity.medicineEntity.dosage
                        medicineTypeTxt.text = medicineData.medicineEntity.typeEntity.typeName
                        medicineDosageSuffixTxt.text = medicineData.medicineEntity.typeEntity.pcsName
                        root.setOnClickListener { onItemClick(medicineData.medicineEntity.medicineEntity) }
                    }
                }
            )
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        return adapter
    }

    inner class EmptyHolder(view: View) : RecyclerViewHolder<MedicineModel.EmptyMedicine>(view) {
        val binding = ItemEmptyBinding.bind(view)
    }

    inner class MedicineHolder(view: View) : RecyclerViewHolder<MedicineModel.MedicineData>(view) {
        val binding = ItemMedicineBinding.bind(view)
    }
}