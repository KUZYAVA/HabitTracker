package com.kuzyava.habittrackerapp.data

import com.kuzyava.habittrackerapp.data.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(type: IType): Flow<List<Habit>>
    fun findHabit(id: String): Flow<Habit>
    suspend fun addHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
}