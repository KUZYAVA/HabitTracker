package com.kuzyava.habittrackerapp.data.remote

import com.kuzyava.habittrackerapp.data.model.Habit
import com.kuzyava.habittrackerapp.data.model.HabitUID
import com.kuzyava.habittrackerapp.data.model.NewHabit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

private const val BASE_URL =
    "https://droid-test-server.doubletapp.ru/api/"
private const val Authorization = "8f7c6ecb-76a1-4f29-a365-2e88743dd957"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

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
    suspend fun add(@Body habit: NewHabit): HabitUID

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Authorization: $Authorization"
    )
    @PUT("habit")
    suspend fun update(@Body habit: Habit)
}

object HabitApi {
    val retrofitService: HabitApiService by lazy {
        retrofit.create(HabitApiService::class.java)
    }
}