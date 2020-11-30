package com.enclave.barry.recipeapp.app.main.vm

import com.enclave.barry.recipeapp.base.BaseViewModel
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepository
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    recipeRepository: RecipeRepository
) : BaseViewModel() {
    init {
        compositeDisposable.addAll(recipeRepository.updateListTypes().subscribe({
            Timber.d("Insert Success!")
        }, {
            Timber.d("Insert Failed!")
            it.printStackTrace()
        }))
    }
}