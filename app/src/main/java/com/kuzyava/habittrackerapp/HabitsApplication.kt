package com.kuzyava.habittrackerapp

import android.app.Application
import com.kuzyava.habittrackerapp.db.HabitRepository
import com.kuzyava.habittrackerapp.db.HabitRoomDatabase

class HabitsApplication : Application() {
    private val database by lazy { HabitRoomDatabase.getDatabase(this) }
    val repository by lazy { HabitRepository(database.habitDao()) }
}