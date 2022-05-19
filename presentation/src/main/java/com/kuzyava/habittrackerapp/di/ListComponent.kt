package com.kuzyava.habittrackerapp.di

import com.kuzyava.habittrackerapp.ui.home.HomeFragment
import com.kuzyava.habittrackerapp.ui.list.ListFragment
import dagger.Subcomponent
import javax.inject.Scope

@ListScope
@Subcomponent
abstract class ListComponent : ScopedComponent() {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ListComponent
    }

    abstract fun inject(homeFragment: HomeFragment)
    abstract fun inject(listFragment: ListFragment)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ListScope