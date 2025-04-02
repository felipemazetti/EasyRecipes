package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.RecipeDetailViewModel
import com.devspace.myapplication.detail.presentation.RecipeDetailScreen
import com.devspace.myapplication.list.RecipeListViewModel
import com.devspace.myapplication.list.presentation.MainScreen
import com.devspace.myapplication.search.SearchRecipeViewModel
import com.devspace.myapplication.search.presentation.SearchRecipesScreen

@Composable
fun AppNavigation(
    listViewModel: RecipeListViewModel,
    detailViewModel: RecipeDetailViewModel,
    searchViewModel: SearchRecipeViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding_screen") {
        composable(route = "onboarding_screen") {
            OnboardingScreen(navController)
        }
        composable(route = "main_screen") {
            MainScreen(navController, listViewModel)
        }
        composable(route = "recipe_detail_screen/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(id, navController, detailViewModel)
        }
        composable(
            route = "search_recipes_screen" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController, searchViewModel)
        }


    }

}




