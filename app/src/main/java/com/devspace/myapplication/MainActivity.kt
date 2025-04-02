package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devspace.myapplication.detail.RecipeDetailViewModel
import com.devspace.myapplication.list.RecipeListViewModel
import com.devspace.myapplication.search.SearchRecipeViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import kotlin.getValue


class MainActivity : ComponentActivity() {
    private val listViewModel by viewModels<RecipeListViewModel> {RecipeListViewModel.factory}
    private val detailViewModel by viewModels< RecipeDetailViewModel> {RecipeDetailViewModel.factory}
    private val searchViewModel by viewModels<SearchRecipeViewModel> {SearchRecipeViewModel.factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            EasyRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(listViewModel = listViewModel,
                        detailViewModel = detailViewModel,
                        searchViewModel = searchViewModel)

                }
            }
        }
    }
}




