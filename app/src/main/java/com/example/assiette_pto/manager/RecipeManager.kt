package com.example.assiette_pto.manager

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.assiette_pto.responses.Meal
import com.example.assiette_pto.responses.Recipe

object RecipeManager {

    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()
    private val recipesCollection = db.collection("recipes")

    fun getAllRecipes(onSuccess: (List<Meal>) -> Unit, onFailure: (Exception) -> Unit) {
        recipesCollection.get()
            .addOnSuccessListener { snapshot ->
                val recipes = snapshot.documents.mapNotNull { it.toObject(Meal::class.java) }
                onSuccess(recipes)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun searchRecipes(query: String, onSuccess: (List<Meal>) -> Unit, onFailure: (Exception) -> Unit) {
        recipesCollection
            .whereEqualTo("name", query)
            .get()
            .addOnSuccessListener { snapshot ->
                val recipes = snapshot.documents.mapNotNull { it.toObject(Meal::class.java) }
                onSuccess(recipes)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
    fun getUserRecipes(onSuccess: (List<Recipe>) -> Unit, onFailure: (Exception) -> Unit) {
        recipesCollection.get()
            .addOnSuccessListener { snapshot ->
                val recipes = snapshot.documents.mapNotNull { it.toObject(Recipe::class.java) }
                onSuccess(recipes)
            }
            .addOnFailureListener { e ->
                Log.e("RecipesManager", "Failed to fetch user recipes", e)
                onFailure(e)
            }
    }
}
