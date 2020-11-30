package com.enclave.barry.recipeapp.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.enclave.barry.recipeapp.di.ViewModelsFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseBindingActivity<TBinding : ViewDataBinding, TViewModel : BaseViewModel> :
    BaseActivity() {

    @Inject
    lateinit var factory: ViewModelsFactory
    lateinit var viewModel: TViewModel
    lateinit var binding: TBinding

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: KClass<TViewModel>

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[viewModelClass.java]
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initView(savedInstanceState)
    }

    fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}