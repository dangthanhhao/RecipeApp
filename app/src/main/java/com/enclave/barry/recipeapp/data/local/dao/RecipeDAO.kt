package com.enclave.barry.recipeapp.data.local.dao

import androidx.room.*
import com.enclave.barry.recipeapp.data.local.model.Recipe
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RecipeDAO {
    @Insert
    fun insert(recipe: Recipe): Completable

    @Insert
    fun insertList(recipes: List<Recipe>): Completable

    @Delete
    fun delete(recipe: Recipe): Completable

    @Update
    fun update(recipe: Recipe): Completable

    @Query("DELETE FROM Recipe")
    fun deleteAll(): Completable

    @Query("SELECT * FROM Recipe")
    fun getAlL(): Flowable<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE id= :recipeId LIMIT 1")
    fun getByID(recipeId: Int): Single<Recipe>

    @Query("SELECT * FROM Recipe")
    fun getAllSingle(): Single<List<Recipe>>
}