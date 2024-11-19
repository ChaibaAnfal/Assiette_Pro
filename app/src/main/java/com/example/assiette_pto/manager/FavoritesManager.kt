package com.example.assiette_pto.manager

import android.util.Log
import com.example.assiette_pto.responses.Meal
import com.google.firebase.firestore.FirebaseFirestore

object FavoritesManager {
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val favoritesCollection by lazy {
        db.collection("favorites")
    }

    fun addFavorite(meal: Meal) {
        favoritesCollection.document(meal.id).set(meal)
            .addOnSuccessListener {
                Log.d("FavoritesManager", "Meal added to Firestore: ${meal.name}")
            }
            .addOnFailureListener { e ->
                Log.e("FavoritesManager", "Failed to add meal to Firestore", e)
            }
    }

    fun removeFavorite(meal: Meal) {
        favoritesCollection.document(meal.id).delete()
            .addOnSuccessListener {
                Log.d("FavoritesManager", "Meal removed from Firestore: ${meal.name}")
            }
            .addOnFailureListener { e ->
                Log.e("FavoritesManager", "Failed to remove meal from Firestore", e)
            }
    }

    fun isFavorite(meal: Meal, onResult: (Boolean) -> Unit) {
        favoritesCollection.document(meal.id).get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener { e ->
                Log.e("FavoritesManager", "Failed to check favorite status", e)
                onResult(false)
            }
    }

    fun getFavorites(onSuccess: (List<Meal>) -> Unit, onFailure: (Exception) -> Unit) {
        favoritesCollection.get()
            .addOnSuccessListener { snapshot ->
                val meals = snapshot.documents.mapNotNull { it.toObject(Meal::class.java) }
                onSuccess(meals)
            }
            .addOnFailureListener { e ->
                Log.e("FavoritesManager", "Failed to fetch favorites from Firestore", e)
                onFailure(e)
            }
    }
}
