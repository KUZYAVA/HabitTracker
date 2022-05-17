package com.kuzyava.habittrackerapp.data

import com.kuzyava.habittrackerapp.data.model.Habit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadHabitsInteractor @Inject constructor(private val repository: HabitRepository) {
    fun getHabits(type: IType) = repository.getHabits(type)
    fun findHabit(id: String) = repository.findHabit(id)
    suspend fun addHabit(habit: Habit) = repository.addHabit(habit)
    suspend fun updateHabit(habit: Habit) = repository.updateHabit(habit)
    suspend fun executeHabit(habit: Habit): String {
        repository.updateHabit(habit.apply {
            amount += 1
            date = (Date().time / 1000).toInt()
        })

        val period = habit.periodicity
        val amount = habit.amount
        val type = habit.type

        return if (amount < period) {
            val diff = period - amount
            if (type == 0) "Стоит выполнить еще $diff раз" else "Можете выполнить еще $diff раз"
        } else {
            if (type == 0) "You are breathtaking!" else "Хватит это делать"
        }
    }
}