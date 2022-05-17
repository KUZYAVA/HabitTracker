package com.kuzyava.habittrackerapp.data.remote

import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.model.HabitUID
import com.kuzyava.habittrackerapp.data.model.NewHabit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

private const val Authorization = "8f7c6ecb-76a1-4f29-a365-2e88743dd957"

interface HabitApiService {
    @Headers(
        "Accept: application/json",
        "Authorization: $Authorization"
    )
    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Authorization: $Authorization"
    )
    @PUT("habit")
    suspend fun addHabit(@Body habit: NewHabit): HabitUID

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Authorization: $Authorization"
    )
    @PUT("habit")
    suspend fun updateHabit(@Body habit: Habit)
}