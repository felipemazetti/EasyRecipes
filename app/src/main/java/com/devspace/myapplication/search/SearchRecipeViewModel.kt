package com.devspace.myapplication.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.SearchRecipeDto
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SearchRecipeViewModel (
    private val searchService: SearchService
): ViewModel() {

    private val _uiSearchRecipes = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiSearchRecipes: StateFlow<List<SearchRecipeDto>> = _uiSearchRecipes


    fun fetchSearchRecipes(query: String){
        if (_uiSearchRecipes.value.isEmpty()) {
            viewModelScope.launch (Dispatchers.IO) {
                val response = searchService.searchRecipes(query)
                if (response.isSuccessful) {
                    _uiSearchRecipes.value = response.body()?.results ?: emptyList()
                } else {
                    Log.e("SearchRecipesScreen", "Error: ${response.errorBody()}")
                }
            }
        }
    }



    companion object {
        val factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofit.create(SearchService::class.java)
                return SearchRecipeViewModel(
                    searchService
                ) as T
            }
        }
    }
}