package com.kuzyava.habittrackerapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.HabitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: HabitRepository, private val habitId: String) :
    ViewModel() {
    private val mutableHabitId = MutableLiveData<String>()
    val habit = Transformations.switchMap(mutableHabitId) {
        repository.findById(it)
    }

    init {
        if (habitId != HABIT_ID_ADD_NEW) mutableHabitId.value = habitId
    }

    fun addHabit(habit: Habit) {
        CoroutineScope(Dispatchers.IO).launch {
            if (habitId != HABIT_ID_ADD_NEW)
                repository.update(habit)
            else
                repository.add(habit)
        }
    }
}