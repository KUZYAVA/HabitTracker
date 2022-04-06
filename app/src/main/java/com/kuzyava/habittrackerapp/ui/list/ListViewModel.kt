package com.kuzyava.habittrackerapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.model.Habit
import com.kuzyava.habittrackerapp.model.HabitsLab

class ListViewModel : ViewModel() {
    private val mutableHabitsList: MutableLiveData<List<Habit>> = MutableLiveData()
    val habitsList: LiveData<List<Habit>> = mutableHabitsList

    init {
        load()
    }

    fun load() {
        mutableHabitsList.value = HabitsLab.habits
    }

    fun getHabits() = HabitsLab.habits

    fun sortByAmount(direction: Boolean) = if (direction) {
        mutableHabitsList.setValue(HabitsLab.habits.sortedBy { it.amount.toInt() })
    } else {
        mutableHabitsList.setValue(HabitsLab.habits.sortedByDescending { it.amount.toInt() })
    }

    fun sortByPeriod(direction: Boolean) = if (direction) {
        mutableHabitsList.setValue(HabitsLab.habits.sortedBy { it.periodicity.toInt() })
    } else {
        mutableHabitsList.setValue(HabitsLab.habits.sortedByDescending { it.periodicity.toInt() })
    }

    fun filterByTitle(text: String) {
        val q = text.length
        mutableHabitsList.value = HabitsLab.habits.filter { it.title.take(q) == text }
    }
}

