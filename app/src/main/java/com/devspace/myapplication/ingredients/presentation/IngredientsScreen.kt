package com.devspace.myapplication.ingredients.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devspace.myapplication.IngredientDto
import com.devspace.myapplication.ingredients.IngredientsViewModel
import java.net.URLDecoder
import java.util.Locale


@Composable
fun IngredientScreen(
    id: String,
    title: String,
    navController: NavController,
    ingredientViewModel: IngredientsViewModel,
) {
    val ingredientDto by ingredientViewModel.uiRecipeIngredients.collectAsState()

    LaunchedEffect(id) {
        ingredientViewModel.fetchRecipeIngredients(id)
    }



    ingredientDto.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = URLDecoder.decode(title, "UTF-8")
                )
            }
            IngredientContent(ingredient = ingredientDto)
        }
    }

}

@Composable
fun IngredientContent(
    modifier: Modifier = Modifier,
    ingredient: List<IngredientDto>
) {
    LazyColumn(
        modifier = modifier.padding(12.dp)
    ) {
        items(ingredient) {
            IngredientItem(
                ingredientDto = it
            )
        }


    }

}

@Composable
fun IngredientItem(
    ingredientDto: IngredientDto
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .height(150.dp),
            contentScale = ContentScale.Fit,
            model = "https://spoonacular.com/cdn/ingredients_250x250/${ingredientDto.image}",
            contentDescription = "${ingredientDto.name} Ingredient Image"
        )
        Spacer(
            modifier = Modifier.padding(2.dp),
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = ingredientDto.name.toUpperCase(Locale.ROOT),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Row {

            Text(
                modifier = Modifier,
                text = ingredientDto.amount.metric.value.toString(),
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier,
                text = ingredientDto.amount.metric.unit,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))


    }

}