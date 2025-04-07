package com.devspace.myapplication.ingredients.data

import com.devspace.myapplication.IngredientsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IngredientService  {
    @GET("recipes/{id}/ingredientWidget.json")
    suspend fun getIngredientInformation(@Path("id") id: String): Response<IngredientsResponse>

}