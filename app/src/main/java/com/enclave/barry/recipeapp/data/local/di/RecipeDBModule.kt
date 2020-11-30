package com.enclave.barry.recipeapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.enclave.barry.recipeapp.data.local.RecipeDB
import com.enclave.barry.recipeapp.data.local.dao.RecipeDAO
import com.enclave.barry.recipeapp.data.local.dao.RecipeTypeDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeDBModule {
    @Provides
    @Singleton
    fun provideRecipeDB(context: Context): RecipeDB {
        return Room.databaseBuilder(
            context.applicationContext,
            RecipeDB::class.java,
            "recipes-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDAO(recipeDB: RecipeDB): RecipeDAO {
        return recipeDB.recipeDAO
    }

    @Provides
    @Singleton
    fun provideRecipeTypeDAO(recipeDB: RecipeDB): RecipeTypeDAO {
        return recipeDB.recipeTypeDAO
    }
}