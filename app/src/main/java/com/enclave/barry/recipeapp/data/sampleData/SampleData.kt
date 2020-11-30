package com.enclave.barry.recipeapp.data.sampleData

import com.enclave.barry.recipeapp.data.local.model.Ingredient
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.data.local.model.Step

object SampleData {
    fun getSampleData(): List<Recipe> {
        return listOf(
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
    }
}