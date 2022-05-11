package com.kuzyava.habittrackerapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import com.kuzyava.habittrackerapp.HabitsApplication
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.databinding.FragmentDetailBinding
import java.util.*

const val HABIT_ID_ADD_NEW = ""
val priorityList = listOf("Высокий", "Средний", "Низкий")

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var habitId = HABIT_ID_ADD_NEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                habitId = arguments?.getString(ID_KEY) ?: HABIT_ID_ADD_NEW
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(
                    (activity?.application as HabitsApplication).repository,
                    habitId
                ) as T
            }
        })[DetailViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.btnColor.tag = Color.BLACK
        viewModel.habit.observe(viewLifecycleOwner) { habit ->
            binding.tfTitle.editText?.setText(habit.title)
            binding.tfDesc.editText?.setText(habit.description)
            binding.tfPriority.editText?.setText(priorityList[habit.priority])
            if (habit.type == 0) {
                binding.radioButton1.isChecked = true
            } else {
                binding.radioButton2.isChecked = true
            }
            binding.tfAmount.editText?.setText(habit.amount.toString())
            binding.tfPeriodicity.editText?.setText(habit.periodicity.toString())
            binding.btnColor.setBackgroundColor(habit.color)
            binding.btnColor.tag = habit.color
        }

        val adapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            priorityList
        )
        (binding.tfPriority.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.btnSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.tfTitle.editText?.text.toString())
                or TextUtils.isEmpty(binding.tfDesc.editText?.text.toString())
                or TextUtils.isEmpty(binding.tfAmount.editText?.text.toString())
                or TextUtils.isEmpty(binding.tfPeriodicity.editText?.text.toString())
                or TextUtils.isEmpty(binding.tfPriority.editText?.text.toString())
            ) {
                Toast.makeText(context, "Заполните все данные", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val habit = Habit(
                uid = if (habitId != HABIT_ID_ADD_NEW) habitId else "",
                title = binding.tfTitle.editText?.text.toString(),
                description = binding.tfDesc.editText?.text.toString(),
                priority = priorityList.indexOf(binding.tfPriority.editText?.text.toString()),
                type = if (binding.rgType.checkedRadioButtonId == R.id.radioButton1) 0 else 1,
                amount = binding.tfAmount.editText?.text.toString().toInt(),
                periodicity = binding.tfPeriodicity.editText?.text.toString().toInt(),
                color = binding.btnColor.tag as Int,
                date = (Date().time / 1000).toInt()
            )
            viewModel.addHabit(habit)
            Toast.makeText(context, "Данные сохранены", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.btnColor.setOnClickListener {
            val dialog = ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowPresets(true)
                .setAllowCustom(false)
                .setColorShape(ColorShape.SQUARE).create()

            dialog.setColorPickerDialogListener(object : ColorPickerDialogListener {
                override fun onColorSelected(dialogId: Int, color: Int) {
                    binding.btnColor.setBackgroundColor(color)
                    binding.btnColor.tag = color
                }

                override fun onDialogDismissed(dialogId: Int) {
                }
            })
            dialog.show(childFragmentManager, "color-picker-dialog")
        }
        return binding.root
    }

    companion object {
        const val ID_KEY = "id"
    }
}