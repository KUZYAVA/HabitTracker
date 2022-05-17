package com.kuzyava.habittrackerapp.data

import android.util.Log
import com.kuzyava.habittrackerapp.data.db.*
import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.model.convertToNewHabit
import com.kuzyava.habittrackerapp.data.remote.HabitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HabitRepositoryImpl(
    private val habitDao: HabitDao,
    private val retrofitService: HabitApiService
) : HabitRepository {
    private val customId = "custom"

    override fun getHabits(type: IType) =
        when (type) {
            is Refresh -> refreshHabitsFromNetwork()
            is SortType -> getSortedHabits(type)
            is FilterType -> filterHabits(type)
        }

    private fun getSortedHabits(type: SortType) = when (type) {
        SortType.SortDefault -> {
            Log.d("HabitRepository", "getSortedHabits: sortdefault")
            habitDao.getHabits()
        }
        SortType.SortByAmount -> habitDao.getHabitsSortByAmount(true)
        SortType.SortByAmountDesc -> habitDao.getHabitsSortByAmount(false)
        SortType.SortByPeriod -> habitDao.getHabitsSortByPeriod(true)
        SortType.SortByPeriodDesc -> habitDao.getHabitsSortByPeriod(false)
    }

    private fun filterHabits(filterType: FilterType) = habitDao.filterHabitsByTitle(filterType.text)

    override fun findHabit(id: String) = habitDao.findHabit(id)

    private fun refreshHabitsFromNetwork() = flow {
        Log.d("HabitRepository", "sort refresh")
        try {
            refreshHabitsFromDb()
            val getFromNetwork = retrofitService.getHabits()
            getFromNetwork.forEach { habitDao.addHabit(it) }
        } catch (e: Exception) {
            Log.d("HabitRepository", "refreshHabits: ${e.message}")
        }
        val loadFromLocal = habitDao.getHabits()
        emitAll(loadFromLocal)
    }.flowOn(Dispatchers.IO)

    private suspend fun refreshHabitsFromDb() {
        val habitsFromDb = habitDao.getHabitsForNetwork()

        val updatedHabits = habitsFromDb.filter { !it.uid.contains(customId) }
        for (h in updatedHabits) {
            try {
                retrofitService.updateHabit(h)
            } catch (e: Exception) {
            }
        }

        val newHabits = habitsFromDb.filter { it.uid.contains(customId) }
        for (h in newHabits) {
            retrofitService.addHabit(convertToNewHabit(h))
            habitDao.deleteHabit(h)
        }
    }

    override suspend fun addHabit(habit: Habit) {
        try {
            val newUid = retrofitService.addHabit(convertToNewHabit(habit))
            habitDao.addHabit(habit.apply {
                uid = newUid.uid
            })
        } catch (e: Exception) {
            habitDao.addHabit(habit.apply {
                uid = "$customId${(0..1000).random()}"
            })
            Log.d("HabitRepository", "add error ${e.message}")
        }
    }

    override suspend fun updateHabit(habit: Habit) {
        try {
            retrofitService.updateHabit(habit)
            habitDao.updateHabit(habit)
        } catch (e: Exception) {
            habitDao.updateHabit(habit)
            Log.d("HabitRepository", "update error ${e.message}")
        }
    }
}
