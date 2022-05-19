package com.kuzyava.habittrackerapp.data

import android.util.Log
import com.kuzyava.habittrackerapp.data.db.HabitDao
import com.kuzyava.habittrackerapp.data.model.Converter
import com.kuzyava.habittrackerapp.data.remote.HabitApiService
import com.kuzyava.habittrackerapp.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HabitRepositoryImpl(
    private val habitDao: HabitDao,
    private val retrofitService: HabitApiService
) : HabitRepository {
    private val customId = "custom"

    override fun getHabits(type: IType): Flow<List<HabitModel>> =
        when (type) {
            is Refresh -> refreshHabitsFromNetwork()
            is SortType -> getSortedHabits(type)
            is FilterType -> filterHabits(type)
        }.map { Converter.convertToListHabitModel(it) }

    private fun getSortedHabits(type: SortType) = when (type) {
        SortType.SortDefault -> habitDao.getHabits()
        SortType.SortByAmount -> habitDao.getHabitsSortByAmount(true)
        SortType.SortByAmountDesc -> habitDao.getHabitsSortByAmount(false)
        SortType.SortByPeriod -> habitDao.getHabitsSortByPeriod(true)
        SortType.SortByPeriodDesc -> habitDao.getHabitsSortByPeriod(false)
    }

    private fun filterHabits(filterType: FilterType) = habitDao.filterHabitsByTitle(filterType.text)

    override fun findHabit(id: String) =
        habitDao.findHabit(id).map { Converter.convertToHabitModel(it) }

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
            retrofitService.addHabit(Converter.convertToNewHabit(h))
            habitDao.deleteHabit(h)
        }
    }

    override suspend fun addHabit(habit: HabitModel): Boolean {
        val newHabit = Converter.convertFromHabitModelTo(habit)
        return try {
            val newUid = retrofitService.addHabit(Converter.convertToNewHabit(newHabit))
            habitDao.addHabit(newHabit.apply {
                uid = newUid.uid
            })
            true
        } catch (e: Exception) {
            habitDao.addHabit(newHabit.apply {
                uid = "$customId${(0..1000).random()}"
            })
            Log.d("HabitRepository", "add error ${e.message}")
            false
        }
    }

    override suspend fun updateHabit(habit: HabitModel): Boolean {
        val updatedHabit = Converter.convertFromHabitModelTo(habit)
        return try {
            retrofitService.updateHabit(updatedHabit)
            habitDao.updateHabit(updatedHabit)
            true
        } catch (e: Exception) {
            habitDao.updateHabit(updatedHabit)
            Log.d("HabitRepository", "update error ${e.message}")
            false
        }
    }
}
