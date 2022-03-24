package com.kuzyava.habittrackerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuzyava.habittrackerapp.model.Habit
import com.kuzyava.habittrackerapp.R
import com.kuzyava.habittrackerapp.databinding.ItemHabitBinding

class HabitsAdapter(private val habits: List<Habit>, val clickListener: (Habit) -> Unit) :
    RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(inflater.inflate(R.layout.item_habit, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        val habit = habits[position]
        holder.bind(habit)
        holder.itemView.setOnClickListener { clickListener(habit) }
    }

    override fun getItemCount() = habits.size

    inner class HabitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHabitBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(habit: Habit) {
            binding.tvTitle.text = habit.title
            binding.tvDesc.text = "Описание: ${habit.description}"
            binding.tvPriority.text = "Приоритет: ${habit.priority}"
            binding.tvType.text = "Тип: ${if (habit.type == 0) "Хорошая" else "Плохая"} привычка"
            binding.tvPeriodicity.text = "Периодичность: ${habit.periodicity}"
            if (habit.color != -1) binding.btnColor2.setColorFilter(habit.color)
        }
    }
}




