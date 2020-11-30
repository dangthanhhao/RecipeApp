package com.enclave.barry.recipeapp.data.local.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.data.local.dao.RecipeDAO
import com.enclave.barry.recipeapp.data.local.dao.RecipeTypeDAO
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.RecipeType
import com.enclave.barry.recipeapp.data.local.model.RecipeTypes
import com.enclave.barry.recipeapp.util.FileUtil
import com.enclave.barry.recipeapp.util.subscribeOnDataThread
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDAO: RecipeDAO,
    private val recipeTypeDAO: RecipeTypeDAO,
    private val context: Context
) : RecipeRepository {

    @SuppressLint("CheckResult")
    override fun updateListTypes(): Completable {
        val xml = FileUtil.readStringFromAssetFile(context, R.raw.recipetypes)
        val obj = FileUtil.xmlToObject(xml, RecipeTypes::class.java)
        return recipeTypeDAO.deleteAll().concatWith(
            recipeTypeDAO.insertList(
                obj.listTypes
            )
        ).subscribeOnDataThread()
    }

    override fun insertRecipe(recipe: Recipe): Completable {
        return recipeDAO.insert(recipe).subscribeOnDataThread()
    }

    override fun insertListRecipes(recipes: List<Recipe>): Completable {
        return recipeDAO.insertList(recipes).subscribeOnDataThread()
    }

    override fun updateRecipe(recipes: Recipe): Completable {
        return recipeDAO.update(recipes).subscribeOnDataThread()
    }

    override fun getRecipes(): LiveData<List<Recipe>> {
        return LiveDataReactiveStreams.fromPublisher(recipeDAO.getAlL().subscribeOnDataThread())
    }

    override fun getRecipe(id: Int): Single<Recipe> {
        return recipeDAO.getByID(id).subscribeOnDataThread()
    }

    override fun deleteRecipe(recipe: Recipe): Completable {
        return recipeDAO.delete(recipe).subscribeOnDataThread()
    }

    override fun getRecipeTypes(): LiveData<List<RecipeType>> {
        return LiveDataReactiveStreams.fromPublisher(recipeTypeDAO.getALL().subscribeOnDataThread())
    }
}