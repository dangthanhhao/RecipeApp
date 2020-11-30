package com.enclave.barry.recipeapp.di.component

import com.enclave.barry.recipeapp.MyApplication
import com.enclave.barry.recipeapp.di.module.ActivityModule
import com.enclave.barry.recipeapp.di.module.AppModule
import com.enclave.barry.recipeapp.di.module.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AndroidInjector<MyApplication>
    }
}