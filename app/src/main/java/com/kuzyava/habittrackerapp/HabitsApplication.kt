package com.kuzyava.habittrackerapp

import android.app.Application
import com.kuzyava.habittrackerapp.data.HabitRepository
import com.kuzyava.habittrackerapp.data.db.HabitRoomDatabase

class HabitsApplication : Application() {
    private val database by lazy { HabitRoomDatabase.getDatabase(this) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}