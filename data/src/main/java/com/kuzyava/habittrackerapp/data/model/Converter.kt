package com.kuzyava.habittrackerapp.data.model

import com.kuzyava.habittrackerapp.domain.HabitModel

class Converter {
    companion object {
        fun convertToNewHabit(habit: Habit) = NewHabit(
            title = habit.title,
            description = habit.description,
            priority = habit.priority,
            type = habit.type,
            amount = habit.amount,
            periodicity = habit.periodicity,
            color = habit.color,
            date = habit.date
        )

        fun convertFromHabitModelTo(habit: HabitModel) = Habit(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            priority = habit.priority,
            type = habit.type,
            amount = habit.amount,
            periodicity = habit.periodicity,
            color = habit.color,
            date = habit.date
        )

        fun convertToHabitModel(habit: Habit) = HabitModel(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            priority = habit.priority,
            type = habit.type,
            amount = habit.amount,
            periodicity = habit.periodicity,
            color = habit.color,
            date = habit.date
        )

        fun convertToListHabitModel(list: List<Habit>): List<HabitModel> {
            return list.map { convertToHabitModel(it) }
        }
    }
}