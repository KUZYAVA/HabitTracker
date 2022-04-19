package com.kuzyava.habittrackerapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kuzyava.habittrackerapp.db.*

class ListViewModel(private val repository: HabitRepository) : ViewModel() {
    private val mutableSortType = MutableLiveData<IType>(SortType.SortDefault)
    val habits = Transformations.switchMap(mutableSortType) {
        repository.getHabits(it)
    }

    fun getHabits(type: IType) {
        mutableSortType.value = type
    }
}