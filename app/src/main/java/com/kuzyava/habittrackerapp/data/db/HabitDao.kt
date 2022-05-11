package com.kuzyava.habittrackerapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kuzyava.habittrackerapp.data.model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getALL(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table")
    fun getAllForNetwork(): List<Habit>

    @Query("SELECT * FROM habit_table WHERE uid=:uid ")
    fun findById(uid: String): LiveData<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Query(
        "SELECT * FROM habit_table ORDER BY "
                + "CASE WHEN :isAsc = 1 THEN amount END ASC,"
                + "CASE WHEN :isAsc = 0 THEN amount END DESC"
    )
    fun getHabitsSortByAmount(isAsc: Boolean): LiveData<List<Habit>>

    @Query(
        "SELECT * FROM habit_table ORDER BY "
                + "CASE WHEN :isAsc = 1 THEN periodicity END ASC,"
                + "CASE WHEN :isAsc = 0 THEN periodicity END DESC"
    )
    fun getHabitsSortByPeriod(isAsc: Boolean): LiveData<List<Habit>>


    @Query("SELECT * FROM habit_table WHERE title LIKE :text || '%'")
    fun filterHabitsByTitle(text: String): LiveData<List<Habit>>
}