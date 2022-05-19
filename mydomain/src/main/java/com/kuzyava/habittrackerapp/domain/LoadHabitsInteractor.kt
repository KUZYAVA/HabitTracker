package com.kuzyava.habittrackerapp.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*

class LoadHabitsInteractor(
    private val repository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    fun getHabits(type: IType) = repository.getHabits(type)
    fun findHabit(id: String) = repository.findHabit(id)
    suspend fun addHabit(habit: HabitModel): Boolean =
        withContext(dispatcher) { return@withContext repository.addHabit(habit) }

    suspend fun updateHabit(habit: HabitModel) =
        withContext(dispatcher) { return@withContext repository.updateHabit(habit) }

    suspend fun executeHabit(habit: HabitModel) = withContext(dispatcher) {
        repository.updateHabit(habit.apply {
            amount += 1
            date = (Date().time / 1000).toInt()
        })

        val period = habit.periodicity
        val amount = habit.amount
        val type = habit.type

        return@withContext if (amount < period) {
            val diff = period - amount
            if (type == 0) "Стоит выполнить еще $diff раз" else "Можете выполнить еще $diff раз"
        } else {
            if (type == 0) "You are breathtaking!" else "Хватит это делать"
        }
    }
}