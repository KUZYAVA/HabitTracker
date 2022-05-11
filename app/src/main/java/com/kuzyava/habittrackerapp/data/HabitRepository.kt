package com.kuzyava.habittrackerapp.data

import android.util.Log
import androidx.lifecycle.liveData
import com.kuzyava.habittrackerapp.data.db.*
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.remote.HabitApi
import com.kuzyava.habittrackerapp.data.model.convertToNewHabit
import kotlinx.coroutines.Dispatchers

const val CUSTOM_UID = "custom"

class HabitRepository(private val habitDao: HabitDao) {
    fun getHabits(type: IType) =
        when (type) {
            is Refresh -> refreshHabitsFromNetwork()
            is SortType -> getSortedHabits(type)
            is FilterType -> filterHabits(type)
        }

    private fun getSortedHabits(type: SortType) = when (type) {
        SortType.SortDefault -> habitDao.getALL()
        SortType.SortByAmount -> habitDao.getHabitsSortByAmount(true)
        SortType.SortByAmountDesc -> habitDao.getHabitsSortByAmount(false)
        SortType.SortByPeriod -> habitDao.getHabitsSortByPeriod(true)
        SortType.SortByPeriodDesc -> habitDao.getHabitsSortByPeriod(false)
    }

    private fun filterHabits(filterType: FilterType) = habitDao.filterHabitsByTitle(filterType.text)

    fun findById(id: String) = habitDao.findById(id)

    private fun refreshHabitsFromNetwork() = liveData(Dispatchers.IO) {
        try {
            refreshHabitsFromDb()
            val getFromNetwork = HabitApi.retrofitService.getHabits()
            getFromNetwork.forEach { habitDao.add(it) }
        } catch (e: Exception) {
            Log.d("HabitRepository", "refreshHabits: ${e.message}")
        }
        val loadFromLocal = habitDao.getALL()
        emitSource(loadFromLocal)
    }

    private suspend fun refreshHabitsFromDb() {
        val habitsFromDb = habitDao.getAllForNetwork()

        val updatedHabits = habitsFromDb.filter { !it.uid.contains(CUSTOM_UID) }
        for (h in updatedHabits) {
            try {
                HabitApi.retrofitService.update(h)
            } catch (e: Exception) {
            }
        }

        val newHabits = habitsFromDb.filter { it.uid.contains(CUSTOM_UID) }
        for (h in newHabits) {
            HabitApi.retrofitService.add(convertToNewHabit(h))
            habitDao.delete(h)
        }
    }

    suspend fun add(habit: Habit) {
        try {
            val uid = HabitApi.retrofitService.add(convertToNewHabit(habit))
            habit.uid = uid.uid
            habitDao.add(habit)
        } catch (e: Exception) {
            habit.uid = "$CUSTOM_UID${(0..1000).random()}"
            habitDao.add(habit)
            Log.d("HabitRepository", "add error ${e.message}")
        }
    }

    suspend fun update(habit: Habit) {
        try {
            HabitApi.retrofitService.update(habit)
            habitDao.update(habit)
        } catch (e: Exception) {
            habitDao.update(habit)
            Log.d("HabitRepository", "update error ${e.message}")
        }
    }
}
