package com.kuzyava.habittrackerapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.db.Habit
import com.kuzyava.habittrackerapp.db.HabitRepository

class DetailViewModel(private val repository: HabitRepository, private val id: Int) :
    ViewModel() {
    private val mutableHabitId = MutableLiveData<Int>()
    val habit = Transformations.switchMap(mutableHabitId) {
        repository.findById(it)
    }

    init {
        if (id != -1) mutableHabitId.value = id
    }

    fun addHabit(habit: Habit) =
        if (id != -1) repository.update(habit)
        else repository.insert(habit)
}