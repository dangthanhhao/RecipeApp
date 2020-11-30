package com.enclave.barry.recipeapp.app.main.ui

import android.os.Bundle
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.app.main.vm.MainViewModel
import com.enclave.barry.recipeapp.base.BaseBindingActivity
import com.enclave.barry.recipeapp.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class

    override fun initView(savedInstanceState: Bundle?) {

    }

}