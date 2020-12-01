package com.enclave.barry.recipeapp.di.module

import androidx.lifecycle.ViewModel
import com.enclave.barry.recipeapp.data.api.repository.LoginRepository
import com.enclave.barry.recipeapp.data.api.repository.LoginRepositoryImpl
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepository
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

//for viewModels
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

//for repositories
@Module
abstract class DataModule {
    @Binds
    abstract fun recipeRepository(repository: RecipeRepositoryImpl): RecipeRepository

    @Binds
    abstract fun loginRepository(repository: LoginRepositoryImpl): LoginRepository
}
