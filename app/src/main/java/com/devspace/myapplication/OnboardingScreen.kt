package com.devspace.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController

@Composable
fun OnboardingScreen(navHostController: NavHostController) {
    OnboardingContent(
        onClick ={
            navHostController.navigate("main_screen")
        }
    )
}

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = "Onboarding Image with a burguer in background"
        )
        Surface(
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp,
                    color = Color.White,
                    text = "Let's \n Cooking"
                )
                Text(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    text = "Find best recipes for cooking"
                )

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
                    Text(color = Color.White,
                        text = "Start Cooking")
                }


            }
        }
    }

}
