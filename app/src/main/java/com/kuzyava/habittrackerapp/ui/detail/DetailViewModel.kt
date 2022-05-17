package com.kuzyava.habittrackerapp.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.LoadHabitsInteractor
import com.kuzyava.habittrackerapp.di.DetailScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@DetailScope
class DetailViewModel @Inject constructor(
    private val loadHabitsInteractor: LoadHabitsInteractor,
    private val habitId: String
) :
    ViewModel() {
    private val mutableHabitId = MutableLiveData<String>()
    val habit = Transformations.switchMap(mutableHabitId) {
        loadHabitsInteractor.findHabit(it).asLiveData()
    }

    init {
        Log.d("DetailViewModel", "init vm DetailViewModel")
        if (habitId != HABIT_ID_ADD_NEW) mutableHabitId.value = habitId
    }

    fun addHabit(habit: Habit) {
        CoroutineScope(Dispatchers.IO).launch {
            if (habitId != HABIT_ID_ADD_NEW)
                loadHabitsInteractor.updateHabit(habit)
            else
                loadHabitsInteractor.addHabit(habit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DetailViewModel", "clear vm DetailViewModel ")
    }
}