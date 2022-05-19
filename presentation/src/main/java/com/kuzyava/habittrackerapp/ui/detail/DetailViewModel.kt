package com.kuzyava.habittrackerapp.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.kuzyava.habittrackerapp.domain.LoadHabitsInteractor
import com.kuzyava.habittrackerapp.di.DetailScope
import com.kuzyava.habittrackerapp.domain.HabitModel
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
    val toast = MutableLiveData<String>()

    init {
        Log.d("DetailViewModel", "init vm DetailViewModel")
        if (habitId != HABIT_ID_ADD_NEW) mutableHabitId.value = habitId
    }

    fun addHabit(habit: HabitModel) {
        viewModelScope.launch {
            val result = if (habitId != HABIT_ID_ADD_NEW)
                loadHabitsInteractor.updateHabit(habit)
            else
                loadHabitsInteractor.addHabit(habit)
            toast.value =
                if (result) "Данные сохранены" else "Не удалось загрузить данные в интернет"
        }
    }
}