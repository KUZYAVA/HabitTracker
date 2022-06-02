package com.kuzyava.habittrackerapp.data

import com.kuzyava.habittrackerapp.domain.HabitModel
import com.kuzyava.habittrackerapp.domain.SortType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

class HabitRepositoryTest {
    @Test
    fun addHabits() = runBlockingTest {
        val habitsRepository = FakeHabitRepository()
        val habit = HabitModel(
            "1", "hello", "yea", 0, 0, 10, 10, 0, 0
        )
        habitsRepository.addHabit(
            habit
        )
        habitsRepository.addHabit(
            habit.copy(uid = "2")
        )
        habitsRepository.addHabit(
            habit.copy(uid = "3")
        )
        val actual = habitsRepository.getHabits(SortType.SortDefault).single().size
        Assert.assertEquals(3, actual)
    }


    @Test
    fun updateHabit() = runBlockingTest {
        val habitsRepository = FakeHabitRepository()
        val habit = HabitModel(
            "1", "hello", "yea", 0, 0, 10, 10, 0, 0
        )
        habitsRepository.addHabit(
            habit
        )
        habitsRepository.updateHabit(
            habit.copy(title = "privet")
        )
        val actual = habitsRepository.getHabits(SortType.SortDefault).single().first().title
        Assert.assertEquals("privet", actual)
    }

    @Test
    fun findHabit() = runBlockingTest {
        val habitsRepository = FakeHabitRepository()
        val habit = HabitModel(
            "1", "hello", "yea", 0, 0, 10, 10, 0, 0
        )
        habitsRepository.addHabit(
            habit
        )
        val actual = habitsRepository.findHabit(habit.uid).singleOrNull()
        Assert.assertEquals("1", actual?.uid)
    }
}