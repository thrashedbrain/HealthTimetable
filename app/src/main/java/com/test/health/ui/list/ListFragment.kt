package com.test.health.ui.list

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.test.health.R
import com.test.health.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ReceptionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater)
        binding.listFab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_receptionDetailsFragment)
        }

        binding.listRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        val adapter = ReceptionAdapterProvider().getAdapter(
            binding.listRecycler,
            onClearClick = {
                viewModel.getData()
            }
        ) { item ->
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(item.medicineEntity.medicineEntity.name)
                .setMessage(SimpleDateFormat("HH:mm dd-MM-yyyy").format(item.reception.date))
                .setNegativeButton(R.string.delete) { _: DialogInterface, _: Int ->
                    viewModel.deleteReception(item.reception)
                }
            if (!item.reception.done) {
                dialog
                    .setPositiveButton(R.string.done) { _: DialogInterface, _: Int ->
                        viewModel.receptionDone(item.reception)
                    }.setNeutralButton(R.string.change) { _: DialogInterface, _: Int ->
                        findNavController().navigate(
                            R.id.action_listFragment_to_receptionDetailsFragment,
                            bundleOf("reception_id" to item.reception.receptionId)
                        )
                    }
            }
            dialog.show()
        }

        viewModel.receptionData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.getData()

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.searchLayout.setEndIconOnClickListener {
            binding.searchEdit.setText("")
            binding.searchEdit.clearFocus()
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            viewModel.getData()
        }

        binding.filterImg.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
                .build()
            builder.addOnCancelListener { it.dismiss() }
            builder.addOnPositiveButtonClickListener {
                viewModel.getArchiveReceptionsByDate(it)
            }
            builder.show(childFragmentManager, "date")
        }

        return binding.root
    }
}