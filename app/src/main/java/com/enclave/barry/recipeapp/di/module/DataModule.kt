package com.enclave.barry.recipeapp.di.module

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@Module
abstract class DataModule

@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)