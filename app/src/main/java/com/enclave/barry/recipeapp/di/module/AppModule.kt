package com.enclave.barry.recipeapp.di.module

import android.content.Context
import com.enclave.barry.recipeapp.MyApplication
import com.enclave.barry.recipeapp.data.api.di.ApiModule
import com.enclave.barry.recipeapp.data.local.di.RecipeDBModule
import dagger.Module
import dagger.Provides

@Module(includes = [RecipeDBModule::class, ApiModule::class])
class AppModule {
    @Provides
    fun context(): Context {
        return MyApplication.getAppContext()!!
    }
}