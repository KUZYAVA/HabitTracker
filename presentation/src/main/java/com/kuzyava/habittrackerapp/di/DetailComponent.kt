package com.kuzyava.habittrackerapp.di

import com.kuzyava.habittrackerapp.ui.detail.DetailFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@DetailScope
@Subcomponent
abstract class DetailComponent : ScopedComponent() {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance habitId: String): DetailComponent
    }

    abstract fun inject(detailFragment: DetailFragment)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DetailScope