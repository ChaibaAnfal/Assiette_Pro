package com.example.assiette_pto.api_parameters

import com.example.assiette_pto.responses.CategoryResponse
import com.example.assiette_pto.responses.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("search.php")
    fun searchMeals(@Query("s") mealName: String): Call<MealResponse>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") mealId: String): Call<MealResponse>

    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>}
