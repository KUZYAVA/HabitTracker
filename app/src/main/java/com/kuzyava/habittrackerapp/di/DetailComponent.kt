package com.kuzyava.habittrackerapp.di

import com.kuzyava.habittrackerapp.ui.detail.DetailFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@DetailScope
@Subcomponent
interface DetailComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance habitId: String): DetailComponent
    }

    fun inject(detailFragment: DetailFragment)
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class DetailScope