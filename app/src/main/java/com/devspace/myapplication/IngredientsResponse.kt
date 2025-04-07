package com.devspace.myapplication

data class IngredientsResponse(
    val ingredients: List<IngredientDto>
)

data class IngredientDto(
    val name: String,
    val amount: AmountDto,
    val image: String

)

data class AmountDto(
    val metric: MetricDto,
)

data class MetricDto(
    val unit: String,
    val value: Double,
)