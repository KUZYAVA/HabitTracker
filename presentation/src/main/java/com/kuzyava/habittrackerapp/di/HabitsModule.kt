package com.kuzyava.habittrackerapp.di

import android.content.Context
import androidx.room.Room
import com.kuzyava.habittrackerapp.data.HabitRepositoryImpl
import com.kuzyava.habittrackerapp.data.db.HabitDao
import com.kuzyava.habittrackerapp.data.db.HabitRoomDatabase
import com.kuzyava.habittrackerapp.data.remote.HabitApiService
import com.kuzyava.habittrackerapp.domain.HabitRepository
import com.kuzyava.habittrackerapp.domain.LoadHabitsInteractor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class HabitsModule {
    @Singleton
    @Provides
    fun provideLoadHabitsInteractor(repository: HabitRepository): LoadHabitsInteractor {
        return LoadHabitsInteractor(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideRepository(habitDao: HabitDao, retrofitService: HabitApiService): HabitRepository {
        return HabitRepositoryImpl(habitDao, retrofitService)
    }

    @Singleton
    @Provides
    fun provideHabitDao(context: Context): HabitDao {
        val database = Room.databaseBuilder(
            context.applicationContext,
            HabitRoomDatabase::class.java,
            "habit_database"
        )
            .build()
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): HabitApiService {
        return retrofit.create(HabitApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .build()
    }
}