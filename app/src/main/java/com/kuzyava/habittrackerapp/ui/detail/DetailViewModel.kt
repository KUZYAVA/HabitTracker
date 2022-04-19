package com.kuzyava.habittrackerapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.db.Habit
import com.kuzyava.habittrackerapp.db.HabitRepository

class DetailViewModel(private val repository: HabitRepository, private val habitId: Int) :
    ViewModel() {
    private val mutableHabitId = MutableLiveData<Int>()
    val habit = Transformations.switchMap(mutableHabitId) {
        repository.findById(it)
    }

    init {
        if (habitId != HABIT_ID_ADD_NEW) mutableHabitId.value = habitId
    }

    fun addHabit(habit: Habit) =
        if (habitId != HABIT_ID_ADD_NEW) repository.update(habit)
        else repository.insert(habit)
}