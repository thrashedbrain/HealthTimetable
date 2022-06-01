package com.test.health.ui.medicine

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.health.R
import com.test.health.databinding.FragmentMedicineBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicineFragment : Fragment() {

    private val viewModel: MedicineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMedicineBinding.inflate(inflater)
        val adapter = MedicineAdapterProvider().getAdapter(binding.medicineRecycler) {
            AlertDialog.Builder(requireContext())
                .setTitle(it.name)
                .setPositiveButton(R.string.change) { _: DialogInterface, _: Int ->
                    findNavController().navigate(
                        R.id.action_medicineFragment_to_medicineDetailsFragment,
                        bundleOf("medicine_id" to it.medicineId)
                    )
                }.setNegativeButton(R.string.delete) { _: DialogInterface, _: Int ->
                    viewModel.deleteMedicine(it)
                }.show()
        }
        viewModel.getData().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.medicineRecycler.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        binding.medicineFab.setOnClickListener {
            findNavController().navigate(R.id.action_medicineFragment_to_medicineDetailsFragment)
        }
        return binding.root
    }
}