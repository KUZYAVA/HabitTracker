package com.kuzyava.habittrackerapp.db

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var description: String,
    var priority: String,
    var type: Boolean,
    var amount: Int,
    var periodicity: Int,
    var color: Int = Color.BLACK
)