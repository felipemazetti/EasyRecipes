package com.devspace.myapplication.list.data

import com.devspace.myapplication.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET


interface ListService {

    @GET("recipes/random?number=20&include-tag=gluten-free")
    suspend fun getRandomRecipes(): Response<RecipeResponse>


}