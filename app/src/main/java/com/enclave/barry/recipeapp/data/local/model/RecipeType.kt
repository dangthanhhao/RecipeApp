package com.enclave.barry.recipeapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeType(
    @PrimaryKey
    var id: Int?,
    var name: String?
)