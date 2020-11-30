package com.enclave.barry.recipeapp.di.module

import android.content.Context
import com.enclave.barry.recipeapp.MyApplication
import com.enclave.barry.recipeapp.data.local.di.RecipeDBModule
import dagger.Module
import dagger.Provides

@Module(includes = [RecipeDBModule::class])
class AppModule {
    @Provides
    fun context(): Context {
        return MyApplication.getAppContext()!!
    }
}