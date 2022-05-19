package com.kuzyava.habittrackerapp.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(type: IType): Flow<List<HabitModel>>
    fun findHabit(id: String): Flow<HabitModel>
    suspend fun addHabit(habit: HabitModel): Boolean
    suspend fun updateHabit(habit: HabitModel): Boolean
}