package com.kuzyava.habittrackerapp.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.kuzyava.habittrackerapp.domain.IType
import com.kuzyava.habittrackerapp.domain.LoadHabitsInteractor
import com.kuzyava.habittrackerapp.domain.Refresh
import com.kuzyava.habittrackerapp.di.ListScope
import com.kuzyava.habittrackerapp.domain.HabitModel
import kotlinx.coroutines.*
import javax.inject.Inject

@ListScope
class ListViewModel @Inject constructor(private val loadHabitsInteractor: LoadHabitsInteractor) :
    ViewModel() {
    private val mutableSortType = MutableLiveData<IType>()
    val habits = Transformations.switchMap(mutableSortType) {
        loadHabitsInteractor.getHabits(it).asLiveData()
    }
    val toast = MutableLiveData<String>()

    fun getHabits(type: IType) {
        mutableSortType.value = type
    }

    init {
        Log.d("ListViewModel", "init vm ListViewModel")
        getHabits(Refresh)
    }

    fun executeHabit(habit: HabitModel) {
        viewModelScope.launch {
            toast.value = loadHabitsInteractor.executeHabit(habit)
        }
    }
}