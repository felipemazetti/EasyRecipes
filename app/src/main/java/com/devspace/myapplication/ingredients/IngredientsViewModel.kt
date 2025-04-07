package com.devspace.myapplication.ingredients

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.IngredientDto
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.ingredients.data.IngredientService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.java

class IngredientsViewModel(
    private val ingredientService: IngredientService
):ViewModel() {

    private val _uiRecipeIngredients = MutableStateFlow<List<IngredientDto>>(emptyList())
    val uiRecipeIngredients: StateFlow<List<IngredientDto>> = _uiRecipeIngredients


    fun fetchRecipeIngredients(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ingredientService.getIngredientInformation(id)
            if (response.isSuccessful){
                Log.d("API Response", "Ingredientes: ${response.body()}")
                _uiRecipeIngredients.value = response.body()?.ingredients ?: emptyList()
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                Log.e("RecipeDetailViewModel", "Error: $errorMessage")
            }

        }
    }

    companion object{
        val factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val ingredientService = RetrofitClient.retrofit.create(IngredientService::class.java)
                return IngredientsViewModel(
                    ingredientService
                ) as T
            }
        }
    }
}