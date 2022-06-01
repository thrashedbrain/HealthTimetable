package com.test.health.ui.receptionDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.health.R
import com.test.health.databinding.FragmentReceptionDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReceptionDetailsFragment : Fragment() {

    private val viewModel: ReceptionDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReceptionDetailsBinding.inflate(inflater)

        val date = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")

        val id = requireArguments().getLong("reception_id", -1)
        if (id != -1L) {
            val model = viewModel.getModelById(id)
            binding.nameEdit.setText(model.medicineEntity.medicineEntity.name)
            binding.dozeEdit.setText(model.medicineEntity.medicineEntity.dosage)
            binding.dateEdit.setText(format.format(model.reception.date))
            date.time = model.reception.date
            viewModel.setMedicineId(model.medicineEntity.medicineEntity.medicineId)
            viewModel.setDate(date.time)
        }

        binding.dateEdit.setOnClickListener {
            DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    date.set(year, month, dayOfMonth)
                    viewModel.setDate(date.time)
                    binding.dateEdit.setText(format.format(date.time))
                    TimePickerDialog(requireContext(),
                        { _, hourOfDay, minute ->
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            date.set(Calendar.MINUTE, minute)
                            viewModel.setDate(date.time)
                            binding.dateEdit.setText(format.format(date.time))
                        },
                        0, 0, true).show()
                },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show()
        }

        val medicineAdapter = MedicineAdapter(requireContext(), R.layout.item_dropdown, viewModel.getMedicines())
        binding.nameEdit.setAdapter(medicineAdapter)
        binding.nameEdit.setOnItemClickListener { _, _, position, medicineId ->
            if (medicineId == Long.MAX_VALUE) {
                binding.nameEdit.setText("")
                findNavController().navigate(R.id.action_receptionDetailsFragment_to_medicineDetailsFragment)
            } else {
                medicineAdapter.getItem(position)?.name?.let { binding.nameEdit.setText(it) }
                medicineAdapter.getItem(position)?.medicineId?.let { viewModel.setMedicineId(it) }
                medicineAdapter.getItem(position)?.dosage?.let { binding.dozeEdit.setText(it) }
            }
        }

        binding.saveBtn.setOnClickListener {
            if (viewModel.getMedicineId() != -1L) {
                if (id != -1L) {
                    viewModel.update(id)
                    findNavController().popBackStack()
                } else {
                    viewModel.save()
                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.medicine_choose_err), Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}