package com.enclave.barry.recipeapp.di.module

import android.content.Context
import com.enclave.barry.recipeapp.MyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun context(): Context {
        return MyApplication.getAppContext()!!
    }
}