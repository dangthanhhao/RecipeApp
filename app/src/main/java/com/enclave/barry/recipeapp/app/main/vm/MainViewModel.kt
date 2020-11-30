package com.enclave.barry.recipeapp.app.main.vm

import com.enclave.barry.recipeapp.base.BaseViewModel
import com.enclave.barry.recipeapp.data.local.SharedPrefs
import com.enclave.barry.recipeapp.data.local.model.Ingredient
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.Step
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepository
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
                    val listRecipes = listOf(
                        Recipe(
                            null, "Recipe 1", null, "A sample of recipe", 1, listOf(
                                Ingredient(1, "Meat: 300g", 1),
                                Ingredient(2, "Salt: 1/2 cup", 2)
                            ), listOf(
                                Step(1, "Open the application", 1),
                                Step(2, "Create some recipes", 2),
                                Step(3, "Close application", 3)
                            )
                        ),
                        Recipe(
                            null, "Recipe 2", null, "A sample of recipe 2", 1, listOf(
                                Ingredient(1, "Meat: 300g", 1),
                                Ingredient(2, "Salt: 1/2 cup", 2)
                            ), listOf(
                                Step(1, "Open the application", 1),
                                Step(2, "Create some recipes", 2),
                                Step(3, "Close application", 3)
                            )
                        )
                    )
                    return@flatMapCompletable recipeRepository.insertListRecipes(listRecipes)
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