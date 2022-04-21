package com.kuzyava.habittrackerapp.db

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: String,
    val type: Boolean,
    val amount: Int,
    val periodicity: Int,
    val color: Int = Color.BLACK
)