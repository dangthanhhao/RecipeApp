package com.enclave.barry.recipeapp.app.login.di

import androidx.lifecycle.ViewModel
import com.enclave.barry.recipeapp.app.login.vm.LoginViewModel
import com.enclave.barry.recipeapp.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @IntoMap
    @Binds
    @ViewModelKey(LoginViewModel::class)
    abstract fun vm(viewModel: LoginViewModel): ViewModel
}