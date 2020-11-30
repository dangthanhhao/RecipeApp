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

    fun updateIngredientOrder() {
        if (listIngredients.value != null && listIngredients.value!!.isNotEmpty()) {
            var startOrder = 1
            listIngredients.value = listIngredients.value!!.map {
                Ingredient(it.id, it.content, startOrder++)
            }
        }
    }

    fun updateStepOrder() {
        if (listSteps.value != null && listSteps.value!!.isNotEmpty()) {
            var startOrder = 1
            listSteps.value = listSteps.value!!.map {
                Step(it.id, it.content, startOrder++)
            }
        }
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
        listIngredients.value = newIngredients
        updateIngredientOrder()
    }

    fun removeIngredient(ingredientID: Int) {
        listIngredients.value = listIngredients.value!!.filter {
            it.id != ingredientID
        }
        updateIngredientOrder()
    }

    fun updateIngredient(ingredient: Ingredient) {
        if (listIngredients.value != null) {
            listIngredients.value = listIngredients.value!!.map {
                if (it.id == ingredient.id) {
                    return@map ingredient
                } else return@map it
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
        listSteps.value = newSteps
        updateStepOrder()
    }

    fun removeStep(stepID: Int) {
        listSteps.value = listSteps.value!!.filter {
            it.id != stepID
        }
        updateStepOrder()
    }

    fun updateStep(step: Step) {
        if (listSteps.value != null) {
            listSteps.value = listSteps.value!!.map {
                if (it.id == step.id) {
                    return@map step
                } else return@map it
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