package com.kuzyava.habittrackerapp.domain.di

import com.kuzyava.habittrackerapp.domain.HabitRepository
import com.kuzyava.habittrackerapp.domain.LoadHabitsInteractor
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DomainLayerModule {
    @Singleton
    @Provides
    fun provideLoadHabitsInteractor(repository: HabitRepository): LoadHabitsInteractor {
        return LoadHabitsInteractor(repository, Dispatchers.IO)
    }
}