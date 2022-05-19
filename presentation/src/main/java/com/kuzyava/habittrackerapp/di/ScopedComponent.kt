package com.kuzyava.habittrackerapp.di

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

abstract class ScopedComponent : ViewModel()

@MainThread
inline fun <reified C : ScopedComponent> Fragment.components(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factory: () -> C
) = viewModels<C> { viewModelFactoryOf { factory() } }

@MainThread
inline fun <reified VM : ViewModel> viewModelFactoryOf(
    noinline factory: () -> VM
) = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return factory() as VM
    }
}

