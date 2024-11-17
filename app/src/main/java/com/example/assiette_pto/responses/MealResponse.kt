package com.example.assiette_pto.responses
import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>?
)

data class Meal(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strCategory")
    val category: String,
    @SerializedName("strInstructions")
    val instructions: String,
    @SerializedName("strMealThumb")
    val thumbnail: String
)

