package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.designsystem.ERHtmlText
import com.devspace.myapplication.designsystem.ERSearchBar
import com.devspace.myapplication.list.RecipeListViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
    listViewModel: RecipeListViewModel,
) {

    val randomRecipes by listViewModel.recipes.collectAsState()


    Surface (
        modifier = Modifier.fillMaxSize()
    ){
        MainContent(
            recipes = randomRecipes,
            onSearchClicked = { query ->
                val tempCleanQuery = query.trim()
                if (tempCleanQuery.isNotEmpty()) {
                    navHostController.navigate(route = "search_recipes_screen/$tempCleanQuery")
                }
            },
            onClick =
                { itemClicked ->
                    navHostController.navigate(route = "recipe_detail_screen/${itemClicked.id}")
                    Log.d("MainScreen", "ID da receita clicada: ${itemClicked.id}")
                }
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var query by remember { mutableStateOf("") }
        SearchSession(
            label = "Find best recipes \nfor cooking",
            query = query,
            onValueChange = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked

        )
        RecipeSession(
            label = "Recipes",
            recipes = recipes,
            onClick = onClick
        )
    }

}


@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )

    ERSearchBar(
        query = query,
        placeHolder = "Search Recipes",
        onValueChange = onValueChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
    )
}

@Composable
fun RecipeSession(
    label: String,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
){
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )
    RecipeList(
        recipes = recipes,
        onClick = onClick,
    )

}


@Composable
fun RecipeList(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(recipes) {
            RecipeItem(
                recipeDto = it,
                onClick = onClick
            )
        }
    }
}


@Composable
fun RecipeItem(
    recipeDto: RecipeDto,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(recipeDto)
            }

    ) {


        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = recipeDto.image, contentDescription = "${recipeDto.title} Recipe Image",

            )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            text = recipeDto.title,
            fontWeight = FontWeight.SemiBold,
        )


        ERHtmlText(
            text = recipeDto.summary, 3
        )

    }

}