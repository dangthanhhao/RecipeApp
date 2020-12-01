package com.enclave.barry.recipeapp.app.recipeDetail.vm

import androidx.lifecycle.MutableLiveData
import com.enclave.barry.recipeapp.base.BaseViewModel
import com.enclave.barry.recipeapp.data.local.model.Ingredient
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.Step
import com.enclave.barry.recipeapp.data.local.repository.RecipeRepository
import timber.log.Timber
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(val recipeRepo: RecipeRepository) :
    BaseViewModel() {

    val listRecipeTypes = recipeRepo.getRecipeTypes()
    var currentRecipe = MutableLiveData<Recipe>()
    var listIngredients = MutableLiveData<List<Ingredient>>()
    var listSteps = MutableLiveData<List<Step>>()
    val saveResult = MutableLiveData<Boolean>()
    val deleteResult = MutableLiveData<Boolean>()

    init {
        Timber.d("Init viewmodel")
    }

    fun setRecipeType(selectedTypeStr: String) {
        currentRecipe.value!!.type = listRecipeTypes.value!!.findLast {
            it.name.equals(selectedTypeStr)
        }?.id ?: Recipe.DEFAULT_TYPE
    }

    fun initData(recipe: Recipe) {
        currentRecipe.value = recipe
        listIngredients.value = recipe.ingredients
        listSteps.value = recipe.steps
    }

    fun loadRecipe(id: Int) {
        compositeDisposable.add(
            recipeRepo.getRecipe(id).subscribe({
                currentRecipe.postValue(it)
                listIngredients.value = it.ingredients
                listSteps.value = it.steps
            }, {
                Timber.d("ERR when get recipe")
                it.printStackTrace()
            })
        )
    }

    fun updateIngredientOrder(ingredients: List<Ingredient>): List<Ingredient> {
        if (ingredients.isNotEmpty()) {
            var startOrder = 1
            return ingredients.map {
                it.apply {
                    order = startOrder++
                }
            }
        } else return listOf()
    }

    fun updateStepOrder(steps: List<Step>): List<Step> {
        if (steps.isNotEmpty()) {
            var startOrder = 1
            return steps.map {
                it.apply {
                    order = startOrder++
                }
            }
        } else return listOf()
    }

    fun addIngredient(ingredientName: String) {
        var maxIngredientID = 1
        if (!listIngredients.value!!.isNullOrEmpty()) {
            maxIngredientID = listIngredients.value?.maxOf {
                it.id!!
            } ?: 1
        }

        val ingredient = Ingredient(maxIngredientID + 1, ingredientName, -1)

        val newIngredients = mutableListOf<Ingredient>()
        newIngredients.addAll(listIngredients.value!!)
        newIngredients.add(ingredient)
        listIngredients.value = updateIngredientOrder(newIngredients)
    }

    fun removeIngredient(ingredientID: Int) {
        listIngredients.value = updateIngredientOrder(listIngredients.value!!.filter {
            it.id != ingredientID
        })
    }

    fun updateIngredient(ingredient: Ingredient) {
        val oldList = listIngredients.value!!
        oldList.forEach {
            if (it.id == ingredient.id && it != ingredient) {
                listIngredients.value = oldList.map { item ->
                    return@map if (item.id == ingredient.id) ingredient else item
                }
                return
            }
        }
    }

    fun addStep(stepName: String) {
        var maxStepID = 1
        if (!listSteps.value!!.isNullOrEmpty()) {
            maxStepID = listSteps.value?.maxOf {
                it.id!!
            } ?: 1
        }

        val step = Step(maxStepID + 1, stepName, -1)

        val newSteps = mutableListOf<Step>()
        newSteps.addAll(listSteps.value!!)
        newSteps.add(step)
        listSteps.value = updateStepOrder(newSteps)
    }

    fun removeStep(stepID: Int) {
        listSteps.value = updateStepOrder(listSteps.value!!.filter {
            it.id != stepID
        })
    }

    fun updateStep(step: Step) {
        val oldList = listSteps.value!!
        oldList.forEach {
            if (it.id == step.id && it != step) {
                listSteps.value = oldList.map { item ->
                    return@map if (item.id == step.id) step else item
                }
                return
            }
        }
    }

    fun saveRecipe() {
        currentRecipe.value?.let {
            it.ingredients = listIngredients.value!!
            it.steps = listSteps.value!!
        }
        if (currentRecipe.value!!.id != null)
            compositeDisposable.add(
                recipeRepo.updateRecipe(currentRecipe.value!!).subscribe({
                    saveResult.value = true
                }, {
                    it.printStackTrace()
                })
            )
        else
            compositeDisposable.add(
                recipeRepo.insertRecipe(currentRecipe.value!!).subscribe({
                    saveResult.value = true
                }, {
                    it.printStackTrace()
                })
            )
    }

    fun deleteRecipe() {
        compositeDisposable.add(
            recipeRepo.deleteRecipe(currentRecipe.value!!).subscribe({
                deleteResult.value = true
            }, {
                it.printStackTrace()
            })
        )
    }

    fun setImageRecipe(path: String) {
        currentRecipe.value = currentRecipe.value.apply {
            this?.img = path
        }
    }
}