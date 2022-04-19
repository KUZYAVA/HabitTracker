package com.kuzyava.habittrackerapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getALL(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE id=:id ")
    fun findById(id: Int): LiveData<Habit>

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Query("SELECT * FROM habit_table ORDER BY amount ASC")
    fun getHabitsSortByAmount(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table ORDER BY amount DESC")
    fun getHabitsSortByAmountDesc(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table ORDER BY periodicity ASC")
    fun getHabitsSortByPeriod(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table ORDER BY periodicity DESC")
    fun getHabitsSortByPeriodDesc(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE title LIKE :text || '%'")
    fun filterHabitsByTitle(text: String): LiveData<List<Habit>>
}