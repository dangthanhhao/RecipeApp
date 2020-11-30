package com.enclave.barry.recipeapp

import android.content.Context
import com.enclave.barry.recipeapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApplication : DaggerApplication() {
    companion object {
        private var context: Context? = null
        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        context = applicationContext

    }

    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent.builder().application(this).build()
}