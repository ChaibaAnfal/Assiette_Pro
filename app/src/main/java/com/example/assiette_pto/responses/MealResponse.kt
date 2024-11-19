package com.example.assiette_pto.responses

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>?
)

data class Meal(
    @SerializedName("idMeal")
    var id: String = "",
    @SerializedName("strMeal")
    var name: String = "",
    @SerializedName("strMealThumb")
    var thumbnail: String = "",
    @SerializedName("strInstructions")
    var instructions: String = "",
    // Ingredients and measurements
    @SerializedName("strIngredient1") var ingredient1: String? = null,
    @SerializedName("strIngredient2") var ingredient2: String? = null,
    @SerializedName("strIngredient3") var ingredient3: String? = null,
    @SerializedName("strIngredient4") var ingredient4: String? = null,
    @SerializedName("strIngredient5") var ingredient5: String? = null,
    @SerializedName("strIngredient6") var ingredient6: String? = null,
    @SerializedName("strIngredient7") var ingredient7: String? = null,
    @SerializedName("strIngredient8") var ingredient8: String? = null,
    @SerializedName("strIngredient9") var ingredient9: String? = null,
    @SerializedName("strIngredient10") var ingredient10: String? = null,
    @SerializedName("strIngredient11") var ingredient11: String? = null,
    @SerializedName("strIngredient12") var ingredient12: String? = null,
    @SerializedName("strIngredient13") var ingredient13: String? = null,
    @SerializedName("strIngredient14") var ingredient14: String? = null,
    @SerializedName("strIngredient15") var ingredient15: String? = null,
    @SerializedName("strIngredient16") var ingredient16: String? = null,
    @SerializedName("strIngredient17") var ingredient17: String? = null,
    @SerializedName("strIngredient18") var ingredient18: String? = null,
    @SerializedName("strIngredient19") var ingredient19: String? = null,
    @SerializedName("strIngredient20") var ingredient20: String? = null,
    @SerializedName("strMeasure1") var measure1: String? = null,
    @SerializedName("strMeasure2") var measure2: String? = null,
    @SerializedName("strMeasure3") var measure3: String? = null,
    @SerializedName("strMeasure4") var measure4: String? = null,
    @SerializedName("strMeasure5") var measure5: String? = null,
    @SerializedName("strMeasure6") var measure6: String? = null,
    @SerializedName("strMeasure7") var measure7: String? = null,
    @SerializedName("strMeasure8") var measure8: String? = null,
    @SerializedName("strMeasure9") var measure9: String? = null,
    @SerializedName("strMeasure10") var measure10: String? = null,
    @SerializedName("strMeasure11") var measure11: String? = null,
    @SerializedName("strMeasure12") var measure12: String? = null,
    @SerializedName("strMeasure13") var measure13: String? = null,
    @SerializedName("strMeasure14") var measure14: String? = null,
    @SerializedName("strMeasure15") var measure15: String? = null,
    @SerializedName("strMeasure16") var measure16: String? = null,
    @SerializedName("strMeasure17") var measure17: String? = null,
    @SerializedName("strMeasure18") var measure18: String? = null,
    @SerializedName("strMeasure19") var measure19: String? = null,
    @SerializedName("strMeasure20") var measure20: String? = null,
    var isFavorite: Boolean = false
) {
    // Explicit no-argument constructor
    constructor() : this("", "", "", "", null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null,null,null,
        null , null,null , null,null, null,null,null,false
    )
}
