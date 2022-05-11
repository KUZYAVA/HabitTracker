package com.kuzyava.habittrackerapp.data.model

import android.graphics.Color
import com.squareup.moshi.Json

data class NewHabit(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "priority") val priority: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "count") val amount: Int,
    @Json(name = "frequency") val periodicity: Int,
    @Json(name = "color") val color: Int = Color.BLACK,
    @Json(name = "date") val date: Int
)

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