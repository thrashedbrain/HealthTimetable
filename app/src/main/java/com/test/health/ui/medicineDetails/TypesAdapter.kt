package com.test.health.ui.medicineDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.test.health.R
import com.test.health.data.entities.TypeEntity

class TypesAdapter(
    private val mContext: Context,
    private val layoutResourceId: Int,
    private val types: List<TypeEntity>
) : ArrayAdapter<TypeEntity>(mContext, layoutResourceId, types) {
    override fun getCount(): Int {
        return types.size
    }

    override fun getItem(position: Int): TypeEntity {
        return types[position]
    }

    override fun getItemId(position: Int): Long {
        return types[position].typeId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(layoutResourceId, parent, false)
        }
        try {
            val medicine: TypeEntity? = getItem(position)
            val medicineAutoCompleteView = view!!.findViewById<View>(R.id.nameTxt) as TextView
            medicineAutoCompleteView.text = medicine?.typeName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view!!
    }
}