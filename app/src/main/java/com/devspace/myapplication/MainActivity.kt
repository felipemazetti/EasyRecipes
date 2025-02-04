package com.devspace.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devspace.myapplication.designsystem.ERHtmlText
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            EasyRecipesTheme {
                var randomRecipes by rememberSaveable { mutableStateOf<List<RecipeDto>>(emptyList()) }
                val apiServise = RetrofitClient.retrofit.create(ApiService::class.java)
                val callRandomRecipes = apiServise.getRandomRecipes()

                callRandomRecipes.enqueue(object : Callback<RecipeResponse> {
                    override fun onResponse(
                        call: Call<RecipeResponse>,
                        response: Response<RecipeResponse>
                    ) {
                        if (response.isSuccessful) {
                            val recipeResponse = response.body()?.recipes
                            if (recipeResponse != null) {
                                randomRecipes = recipeResponse
                            }

                        } else {
                            Log.e("MainActivity", "Error: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                        Log.e("MainActivity", "Network Error: ${t.message}")
                    }

                })

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeList(randomRecipes)
                }
            }
        }
    }
}


@Composable
fun RecipeList(recepiList: List<RecipeDto>) {
    LazyColumn {
        items(recepiList) {

            RecipeItem(recipeDto = it)
        }
    }
}


@Composable
fun RecipeItem(
    recipeDto: RecipeDto
) {
    Column(
        modifier = Modifier.padding(8.dp)
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

