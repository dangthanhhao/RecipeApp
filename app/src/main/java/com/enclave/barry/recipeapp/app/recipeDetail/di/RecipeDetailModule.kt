package com.enclave.barry.recipeapp.app.recipeDetail.di

import androidx.lifecycle.ViewModel
import com.enclave.barry.recipeapp.app.recipeDetail.vm.RecipeDetailViewModel
import com.enclave.barry.recipeapp.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RecipeDetailModule {
    @IntoMap
    @Binds
    @ViewModelKey(RecipeDetailViewModel::class)
    abstract fun vm(viewModel: RecipeDetailViewModel): ViewModel
}