package com.kuzyava.habittrackerapp.db

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "priority") val priority: String,
    @ColumnInfo(name = "type") val type: Boolean,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "periodicity") val periodicity: Int,
    @ColumnInfo(name = "color") val color: Int = Color.BLACK
)
