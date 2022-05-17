package com.kuzyava.habittrackerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuzyava.habittrackerapp.data.model.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitRoomDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}