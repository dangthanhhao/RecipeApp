package com.enclave.barry.recipeapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String? = null,
    var img: String? = null,
    var description: String? = null,
    var type: Int = DEFAULT_TYPE,
    var ingredients: List<Ingredient>? = mutableListOf(),
    var steps: List<Step>? = mutableListOf()
) {
    companion object {
        const val DEFAULT_TYPE = 1
    }
}