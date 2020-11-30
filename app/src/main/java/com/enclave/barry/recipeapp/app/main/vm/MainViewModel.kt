package com.enclave.barry.recipeapp.app.main.vm

import com.enclave.barry.recipeapp.base.BaseViewModel
import com.enclave.barry.recipeapp.data.local.SharedPrefs
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepository
import com.enclave.barry.recipeapp.data.sampleData.SampleData
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    recipeRepository: RecipeRepository,
    sharedPrefs: SharedPrefs
) : BaseViewModel() {
    companion object {
        const val FILTER_ALL = "All types"
    }

    val listRecipes = recipeRepository.getRecipes()
    val listRecipeTypes = recipeRepository.getRecipeTypes()

    init {
        //sync xml and room for recipe types
        compositeDisposable.add(
            recipeRepository.updateListTypes().subscribe({
            }, {
                Timber.d("Sync XML with DB failed")
                it.printStackTrace()
            })
        )
        //init some sample data
        checkAndInitData(sharedPrefs, recipeRepository)
    }

    private fun checkAndInitData(
        sharedPrefs: SharedPrefs,
        recipeRepository: RecipeRepository
    ) {
        compositeDisposable.add(
            sharedPrefs.getIsFirstTimeRun().flatMapCompletable {
                if (it) {
                    return@flatMapCompletable recipeRepository.insertListRecipes(SampleData.getSampleData())
                        .andThen(sharedPrefs.setIsfirstTimeRun(false))
                } else Completable.complete()
            }.subscribe({
                Timber.d("Check and init data successfully")
            }, {
                Timber.d("Init data failed")
                it.printStackTrace()
            })
        )
    }

    fun getNameOfRecipeTypeByID(typeID: Int): String? {
        return if (listRecipeTypes.value != null) {
            listRecipeTypes.value!!.find {
                it.id == typeID
            }?.name
        } else null
    }

    fun getListRecipesFiltered(listRecipes: List<Recipe>, typeSearch: String?): List<Recipe> {
        Timber.d("Type search%s", typeSearch)
        return if (typeSearch == null || typeSearch == FILTER_ALL)
            listRecipes
        else listRecipes.filter {
            getNameOfRecipeTypeByID(it.type).equals(typeSearch)
        }
    }

}