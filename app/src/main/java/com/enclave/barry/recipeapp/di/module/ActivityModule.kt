package com.enclave.barry.recipeapp.di.module

import com.enclave.barry.recipeapp.app.main.di.MainModule
import com.enclave.barry.recipeapp.app.main.ui.MainActivity
import com.enclave.barry.recipeapp.app.recipeDetail.di.RecipeDetailModule
import com.enclave.barry.recipeapp.app.recipeDetail.ui.RecipeDetailActivity
import com.enclave.barry.recipeapp.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun main(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [RecipeDetailModule::class])
    abstract fun recipeDetail(): RecipeDetailActivity
}