package com.kuzyava.habittrackerapp.ui.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.databinding.FragmentDetailBinding
import com.kuzyava.habittrackerapp.model.Habit
import com.kuzyava.habittrackerapp.model.HabitsLab

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val position = arguments?.getInt(POSITION_KEY) ?: -1
        if (position != -1) {
            val habit = HabitsLab.habits[position]
            binding.tfTitle.editText?.setText(habit.title)
            binding.tfDesc.editText?.setText(habit.description)
            binding.tfPriority.editText?.setText(habit.priority)
            if (habit.type == 0) {
                binding.radioButton1.isChecked = true
            } else {
                binding.radioButton2.isChecked = true
            }
            binding.tfAmount.editText?.setText(habit.amount)
            binding.tfPeriodicity.editText?.setText(habit.periodicity)
            if (habit.color != -1) binding.btnColor.setBackgroundColor(habit.color)
            binding.btnColor.tag = habit.color
        } else {
            binding.btnColor.tag = -1
        }

        val items = listOf("Высокий", "Средний", "Низкий")
        val adapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            items
        )
        (binding.tfPriority.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val habits = HabitsLab.habits
        binding.btnSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.tfTitle.editText?.text.toString()) or TextUtils.isEmpty(
                    binding.tfDesc.editText?.text.toString()
                )
                or TextUtils.isEmpty(binding.tfAmount.editText?.text.toString()) or TextUtils.isEmpty(
                    binding.tfPeriodicity.editText?.text.toString()
                ) or TextUtils.isEmpty(binding.tfPriority.editText?.text.toString())
            ) {
                Toast.makeText(context, "Заполните все данные", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val habit = Habit(
                title = binding.tfTitle.editText?.text.toString(),
                description = binding.tfDesc.editText?.text.toString(),
                priority = binding.tfPriority.editText?.text.toString(),
                type = if (binding.rgType.checkedRadioButtonId == R.id.radioButton1) 0 else 1,
                amount = binding.tfAmount.editText?.text.toString(),
                periodicity = binding.tfPeriodicity.editText?.text.toString(),
                color = binding.btnColor.tag as Int
            )
            if (position != -1) {
                habits[position] = habit
            } else {
                habits.add(habit)
            }
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
        const val POSITION_KEY = "position"
    }
}