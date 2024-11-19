package com.example.assiette_pto.manager

import com.example.assiette_pto.responses.Meal

object FavoritesManager {
    private val favoriteMeals = mutableListOf<Meal>()

    fun addFavorite(meal: Meal) {
        if (!favoriteMeals.any { it.id == meal.id }) {
            favoriteMeals.add(meal)
        }
    }

    fun removeFavorite(meal: Meal) {
        favoriteMeals.removeAll { it.id == meal.id }
    }

    fun isFavorite(meal: Meal): Boolean {
        return favoriteMeals.any { it.id == meal.id }
    }

    fun getFavorites(): List<Meal> {
        return favoriteMeals
    }
}
