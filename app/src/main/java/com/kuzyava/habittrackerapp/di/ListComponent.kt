package com.kuzyava.habittrackerapp.di

import com.kuzyava.habittrackerapp.MainActivity
import com.kuzyava.habittrackerapp.ui.home.HomeFragment
import com.kuzyava.habittrackerapp.ui.list.ListFragment
import dagger.Subcomponent
import javax.inject.Scope

@ListScope
@Subcomponent
interface ListComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ListComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(listFragment: ListFragment)
    fun inject(mainActivity: MainActivity)
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ListScope