package com.kuzyava.habittrackerapp.data.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "uid") var uid: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "priority") val priority: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "count") val amount: Int,
    @Json(name = "frequency") val periodicity: Int,
    @Json(name = "color") val color: Int = Color.BLACK,
    @Json(name = "date") val date: Int,
)
