package com.kuzyava.habittrackerapp.di

import android.content.Context
import com.kuzyava.habittrackerapp.ui.detail.DetailFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsModule::class, AppSubcomponents::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun listComponent(): ListComponent.Factory
    fun detailComponent(): DetailComponent.Factory

}