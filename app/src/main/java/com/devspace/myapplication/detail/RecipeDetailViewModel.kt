package com.devspace.myapplication.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RecipeDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiRecipeDetail = MutableStateFlow<RecipeDto?>(null)
    val uiRecipeDetail: StateFlow<RecipeDto?> = _uiRecipeDetail


    fun fetchRecipeDetail(id: String) {
        if (_uiRecipeDetail.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getRecipeInformation(id)
                if (response.isSuccessful) {
                    Log.d("API Response", "Receita: ${response.body()}")
                    _uiRecipeDetail.value = response.body()

                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                    Log.e("RecipeDetailViewModel", "Error: $errorMessage")
                }
            }
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofit.create(DetailService::class.java)
                return RecipeDetailViewModel(
                    detailService
                ) as T
            }

        }
    }

}