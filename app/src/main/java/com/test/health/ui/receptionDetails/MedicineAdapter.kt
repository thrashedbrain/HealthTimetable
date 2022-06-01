package com.test.health.ui.receptionDetails

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.test.health.R
import com.test.health.data.entities.MedicineEntity

class MedicineAdapter(
    private val mContext: Context,
    private val layoutResourceId: Int,
    private val medicines: List<MedicineEntity>
) : ArrayAdapter<MedicineEntity>(mContext, layoutResourceId, medicines) {
    override fun getCount(): Int {
        return medicines.size
    }

    override fun getItem(position: Int): MedicineEntity? {
        return medicines[position]
    }

    override fun getItemId(position: Int): Long {
        return medicines[position].medicineId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(layoutResourceId, parent, false)
        }
        try {
            val medicine: MedicineEntity? = getItem(position)
            val medicineAutoCompleteView = view!!.findViewById<View>(R.id.nameTxt) as TextView
            medicineAutoCompleteView.text = medicine?.name
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view!!
    }
}