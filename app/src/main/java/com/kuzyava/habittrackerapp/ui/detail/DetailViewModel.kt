package com.kuzyava.habittrackerapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.model.Habit
import com.kuzyava.habittrackerapp.model.HabitsLab

class DetailViewModel(private val position: Int) : ViewModel() {
    private val mutableDetailHabit: MutableLiveData<Habit> = MutableLiveData()
    val detailHabit: LiveData<Habit> = mutableDetailHabit

    init {
        load()
    }

    private fun load() {
        if (position != -1) {
            mutableDetailHabit.value = HabitsLab.habits[position]
        }
    }

    fun addHabit(habit: Habit) {
        if (position != -1) {
            HabitsLab.habits[position] = habit
        } else {
            HabitsLab.habits.add(habit)
        }
    }
}