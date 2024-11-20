package com.example.assiette_pto.ui.usermeals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.adapters.MealAdapter
import com.example.assiette_pto.databinding.FragmentUserMealsBinding
import com.example.assiette_pto.manager.FavoritesManager
import com.example.assiette_pto.manager.RecipeManager
import com.example.assiette_pto.responses.Meal
import com.example.assiette_pto.responses.Recipe
import com.google.firebase.firestore.FirebaseFirestore

class UserMealsFragment : Fragment() {

    private var _binding: FragmentUserMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserMealsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        fetchUserMeals()

        return root
    }

    private fun setupRecyclerView() {
        mealAdapter = MealAdapter(emptyList()) { meal ->
            // Handle meal click, e.g., navigate to details
        }
        binding.rvUserMeals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserMeals.adapter = mealAdapter
    }

    private fun fetchUserMeals() {
        // Fetch user-added meals from the "recipes" collection
        val userMealsCollection = FirebaseFirestore.getInstance().collection("recipes")

        userMealsCollection.get()
            .addOnSuccessListener { snapshot ->
                // Convert snapshot to a list of meals
                val userMeals = snapshot.documents.mapNotNull { it.toObject(Meal::class.java) }

                // Check favorite status for each meal
                val mealsWithFavorites = userMeals.map { meal ->
                    FavoritesManager.isFavorite(meal) { isFavorite ->
                        meal.isFavorite = isFavorite // Update favorite status
                    }
                    meal
                }

                // Update the adapter with meals
                mealAdapter.updateData(mealsWithFavorites)
            }
            .addOnFailureListener { e ->
                Log.e("UserMealsFragment", "Error fetching user meals", e)
            }
    }


    private fun updateUserMeals(userRecipes: List<Recipe>) {
        val meals = userRecipes.map { recipe ->
            Meal(
                id = recipe.id,
                name = recipe.name,
                thumbnail = recipe.thumbnail,
                instructions = recipe.instructions,
                ingredient1 = recipe.ingredient1,
                ingredient2 = recipe.ingredient2,
                ingredient3 = recipe.ingredient3,
                ingredient4 = recipe.ingredient4,
                ingredient5 = recipe.ingredient5,
                ingredient6 = recipe.ingredient6,
                ingredient7 = recipe.ingredient7,
                ingredient8 = recipe.ingredient8,
                ingredient9 = recipe.ingredient9,
                ingredient10 = recipe.ingredient10,
                ingredient11 = recipe.ingredient11,
                ingredient12 = recipe.ingredient12,
                ingredient13 = recipe.ingredient13,
                ingredient14 = recipe.ingredient14,
                ingredient15 = recipe.ingredient15,
                ingredient16 = recipe.ingredient16,
                ingredient17 = recipe.ingredient17,
                ingredient18 = recipe.ingredient18,
                ingredient19 = recipe.ingredient19,
                ingredient20 = recipe.ingredient20,
                measure1 = recipe.measure1,
                measure2 = recipe.measure2,
                measure3 = recipe.measure3,
                measure4 = recipe.measure4,
                measure5 = recipe.measure5,
                measure6 = recipe.measure6,
                measure7 = recipe.measure7,
                measure8 = recipe.measure8,
                measure9 = recipe.measure9,
                measure10 = recipe.measure10,
                measure11 = recipe.measure11,
                measure12 = recipe.measure12,
                measure13 = recipe.measure13,
                measure14 = recipe.measure14,
                measure15 = recipe.measure15,
                measure16 = recipe.measure16,
                measure17 = recipe.measure17,
                measure18 = recipe.measure18,
                measure19 = recipe.measure19,
                measure20 = recipe.measure20
            )
        }

        mealAdapter.updateData(meals)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
