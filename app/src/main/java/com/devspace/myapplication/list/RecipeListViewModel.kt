package com.devspace.myapplication.list
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.RecipeResponse
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipeListViewModel (
    private val listService: ListService
): ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeDto>>(emptyList())
    val recipes: StateFlow<List<RecipeDto>> = _recipes

    init {
        fetchGetRandomRecipes()
    }

    fun fetchGetRandomRecipes() {
        viewModelScope.launch {
            val response = listService.getRandomRecipes()
            if (_recipes.value.isEmpty()) {
                if (response.isSuccessful) {
                    val recipeResponse = response.body()?.recipes
                    if (recipeResponse != null) {
                        _recipes.value = recipeResponse
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.errorBody()}")
                    Log.e("RecipeDetailScreen", "Erro na API: Código ${response.code()}")
                }
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofit.create(ListService::class.java)
                return RecipeListViewModel(
                    listService
                ) as T
            }
        }
    }
}