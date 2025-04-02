package com.devspace.myapplication

import com.devspace.myapplication.common.model.RecipeDto


data class RecipeResponse(
    val recipes: List<RecipeDto>
)
