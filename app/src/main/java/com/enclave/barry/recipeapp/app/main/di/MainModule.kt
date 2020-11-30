package com.enclave.barry.recipeapp.app.main.di

import androidx.lifecycle.ViewModel
import com.enclave.barry.recipeapp.app.main.vm.MainViewModel
import com.enclave.barry.recipeapp.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {
    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    abstract fun vm(viewModel: MainViewModel): ViewModel
}