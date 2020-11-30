package com.enclave.barry.recipeapp.data.local

import android.content.Context
import androidx.room.*
import com.enclave.barry.recipeapp.data.local.dao.RecipeDAO
import com.enclave.barry.recipeapp.data.local.dao.RecipeTypeDAO
import com.enclave.barry.recipeapp.data.local.model.Ingredient
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.RecipeType
import com.enclave.barry.recipeapp.data.local.model.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    //To store list ingredients and list steps in Rooms DB
    @TypeConverter
    @JvmStatic
    fun fromStringIngredient(value: String?): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListIngredient(list: List<Ingredient?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringStep(value: String?): List<Step> {
        val listType = object : TypeToken<List<Step?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListStep(list: List<Step?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

@Database(
    entities = [Recipe::class, RecipeType::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDB : RoomDatabase() {
    abstract val recipeTypeDAO: RecipeTypeDAO
    abstract val recipeDAO: RecipeDAO

    //Singleton
    companion object {
        private var INSTANCE: RecipeDB? = null
        fun getInstance(context: Context): RecipeDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDB::class.java,
                        "recipes-database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}