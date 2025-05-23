package com.devspace.myapplication.detail.data


import com.devspace.myapplication.common.model.RecipeDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("recipes/{id}/information?includeNutr=false")
    suspend fun getRecipeInformation(@Path("id") id: String): Response<RecipeDto>
}