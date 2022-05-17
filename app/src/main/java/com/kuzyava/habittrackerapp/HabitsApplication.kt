package com.kuzyava.habittrackerapp

import android.app.Application
import com.kuzyava.habittrackerapp.di.ApplicationComponent
import com.kuzyava.habittrackerapp.di.DaggerApplicationComponent

class HabitsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .factory().create(this)
    }
}