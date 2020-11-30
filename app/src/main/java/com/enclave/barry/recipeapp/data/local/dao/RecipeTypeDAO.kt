package com.enclave.barry.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enclave.barry.recipeapp.data.local.model.RecipeType
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RecipeTypeDAO {
    @Insert
    fun insertList(recipeTypes: List<RecipeType>): Completable

    @Query("DELETE FROM RecipeType")
    fun deleteAll(): Completable

    @Query("SELECT * FROM RecipeType")
    fun getALL(): Flowable<List<RecipeType>>

    @Query("SELECT * FROM RecipeType")
    fun getALLSingle(): Single<List<RecipeType>>
}