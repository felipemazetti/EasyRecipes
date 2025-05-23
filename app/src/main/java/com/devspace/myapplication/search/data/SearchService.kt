package com.devspace.myapplication.search.data

import com.devspace.myapplication.SearchRecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("recipes/complexSearch?")
    suspend fun searchRecipes(@Query("query") query: String): Response<SearchRecipesResponse>
}