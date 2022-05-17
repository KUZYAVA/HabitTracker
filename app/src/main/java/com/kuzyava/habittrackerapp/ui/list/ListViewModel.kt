package com.kuzyava.habittrackerapp.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.kuzyava.habittrackerapp.data.IType
import com.kuzyava.habittrackerapp.data.LoadHabitsInteractor
import com.kuzyava.habittrackerapp.data.Refresh
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.di.ListScope
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

    fun executeHabit(habit: Habit) {
        viewModelScope.launch {
            toast.value = loadHabitsInteractor.executeHabit(habit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ListViewModel", "clear vm ListViewModel ")
    }
}