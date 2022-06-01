package com.test.health.ui.medicineDetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.health.R
import com.test.health.data.entities.MedicineEntity
import com.test.health.databinding.FragmentMedicineDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicineDetailsFragment : Fragment() {

    private val viewModel: MedicineDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMedicineDetailsBinding.inflate(inflater)
        val id = requireArguments().getLong("medicine_id", -1)
        if (id != -1L) {
            val model = viewModel.getMedicine(id)
            val typeModel = viewModel.getTypeById(model.typeId)
            binding.nameEdit.setText(model.name)
            binding.descEdit.setText(model.description)
            binding.doseEdit.setText(model.dosage)
            binding.typeEdit.setText(typeModel.typeName)
        }

        binding.saveBtn.setOnClickListener {
            if (viewModel.getType() != -1L
                && binding.nameEdit.text.toString().isNotBlank()
                && binding.doseEdit.text.toString().isNotBlank()
            ) {
                if (id != -1L) {
                    viewModel.updateMedicine(
                        MedicineEntity(
                            medicineId = id,
                            name = binding.nameEdit.text.toString(),
                            description = binding.descEdit.text.toString(),
                            dosage = binding.doseEdit.text.toString(),
                            typeId = viewModel.getType()
                        )
                    )
                } else {
                    viewModel.saveMedicine(
                        MedicineEntity(
                            name = binding.nameEdit.text.toString(),
                            description = binding.descEdit.text.toString(),
                            dosage = binding.doseEdit.text.toString(),
                            typeId = viewModel.getType()
                        )
                    )
                }
                findNavController().popBackStack()
            } else if (binding.nameEdit.text.toString().isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.edit_name_err), Toast.LENGTH_SHORT).show()
            } else if (binding.doseEdit.text.toString().isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.edit_dose_err), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.edit_type_err), Toast.LENGTH_SHORT).show()
            }
        }

        val typesAdapter =
            TypesAdapter(requireContext(), R.layout.item_dropdown, viewModel.getTypes())
        binding.typeEdit.setAdapter(typesAdapter)
        binding.typeEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val inputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
        binding.typeEdit.setOnItemClickListener { _, _, position, typeId ->
            if (typeId == Long.MAX_VALUE) {
                binding.typeEdit.setText("")
            } else {
                binding.typeEdit.setText(typesAdapter.getItem(position)?.typeName)
                typesAdapter.getItem(position)?.typeId?.let { viewModel.setType(it) }
            }
        }

        return binding.root
    }
}