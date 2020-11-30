package com.enclave.barry.recipeapp.data.local.model

import com.google.gson.annotations.SerializedName

data class RecipeTypes(
    @SerializedName("type")
    var listTypes: List<RecipeType>
)