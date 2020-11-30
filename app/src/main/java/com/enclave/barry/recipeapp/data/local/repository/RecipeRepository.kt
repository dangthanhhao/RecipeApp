package com.enclave.barry.recipeapp.data.local.repository

import androidx.lifecycle.LiveData
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.RecipeType
import io.reactivex.Completable
import io.reactivex.Single

interface RecipeRepository {
    fun updateListTypes(): Completable
    fun getRecipeTypes(): LiveData<List<RecipeType>>
    fun insertRecipe(recipes: Recipe): Completable
    fun updateRecipe(recipes: Recipe): Completable
    fun getRecipes(): LiveData<List<Recipe>>
    fun getRecipe(id: Int): Single<Recipe>
    fun deleteRecipe(recipe: Recipe): Completable
}