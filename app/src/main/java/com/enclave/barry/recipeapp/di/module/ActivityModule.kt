package com.enclave.barry.recipeapp.di.module

import com.enclave.barry.recipeapp.app.main.di.MainModule
import com.enclave.barry.recipeapp.app.main.ui.MainActivity
import com.enclave.barry.recipeapp.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun main(): MainActivity
}