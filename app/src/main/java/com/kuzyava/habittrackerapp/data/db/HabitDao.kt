package com.kuzyava.habittrackerapp.data.db

import androidx.room.*
import com.kuzyava.habittrackerapp.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habit_table")
    fun getHabitsForNetwork(): List<Habit>

    @Query("SELECT * FROM habit_table WHERE uid=:uid ")
    fun findHabit(uid: String): Flow<Habit>

    @Query("SELECT * FROM habit_table WHERE uid=:uid ")
    fun getDoneDates(uid: String): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabit(habit: Habit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query(
        "SELECT * FROM habit_table ORDER BY "
                + "CASE WHEN :isAsc = 1 THEN amount END ASC,"
                + "CASE WHEN :isAsc = 0 THEN amount END DESC"
    )
    fun getHabitsSortByAmount(isAsc: Boolean): Flow<List<Habit>>

    @Query(
        "SELECT * FROM habit_table ORDER BY "
                + "CASE WHEN :isAsc = 1 THEN periodicity END ASC,"
                + "CASE WHEN :isAsc = 0 THEN periodicity END DESC"
    )
    fun getHabitsSortByPeriod(isAsc: Boolean): Flow<List<Habit>>


    @Query("SELECT * FROM habit_table WHERE title LIKE :text || '%'")
    fun filterHabitsByTitle(text: String): Flow<List<Habit>>
}