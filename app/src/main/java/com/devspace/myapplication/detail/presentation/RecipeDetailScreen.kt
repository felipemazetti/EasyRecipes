package com.devspace.myapplication.detail.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.designsystem.ERHtmlText
import com.devspace.myapplication.detail.RecipeDetailViewModel
import java.net.URLEncoder


@Composable
fun RecipeDetailScreen(id : String,
                       navHostController: NavHostController,
                       detailViewModel: RecipeDetailViewModel
) {


    val recipeDto by detailViewModel.uiRecipeDetail.collectAsState()
    LaunchedEffect(id) {
        detailViewModel.fetchRecipeDetail(id)
    }



    recipeDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = it.title
                )
            }

            RecipeDetailContent(it)

            Spacer(modifier = Modifier.padding(32.dp))

            IngredientsButton(
                onClick = {
                    navHostController.navigate(route = "ingredient_screen/${it.id.toString()}/${URLEncoder.encode(it.title, "UTF-8")}")
                }
            )

        }

    }

}



    @Composable
    fun RecipeDetailContent(recipe: RecipeDto) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = recipe.image,
                contentDescription = "${recipe.title} Image"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            ERHtmlText(
                text = recipe.summary
            )

        }

    }

@Composable
fun IngredientsButton(
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 32.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Ingredients"
            )
        }
    }

}






