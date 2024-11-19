package com.example.assiette_pto.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.assiette_pto.api_parameters.ApiClient
import com.example.assiette_pto.databinding.FragmentMealDetailBinding
import com.example.assiette_pto.responses.Meal
import com.example.assiette_pto.responses.MealResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailFragment : Fragment() {

    private var _binding: FragmentMealDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailBinding.inflate(inflater, container, false)

        // Retrieve the mealId from arguments
        val mealId = requireArguments().getString("mealId")
        if (mealId != null) {
            fetchMealDetails(mealId)
        } else {
            Toast.makeText(requireContext(), "Meal ID not found", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun fetchMealDetails(mealId: String) {
        val call = ApiClient.mealApi.getMealDetails(mealId)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        displayMealDetails(meal)
                    } else {
                        Toast.makeText(requireContext(), "Meal details not available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch meal details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayMealDetails(meal: Meal) {
        // Set meal name
        binding.tvMealName.text = meal.name

        // Load meal image using Picasso
        Picasso.get().load(meal.thumbnail).into(binding.ivMealImage)

        // Set instructions with a title
        binding.tvInstructionsTitle.text = "Instructions"
        binding.tvInstructions.text = meal.instructions

        // Set ingredients with a title
        binding.tvIngredientsTitle.text = "Ingredients"
        val ingredients = getIngredients(meal)
        binding.tvIngredients.text = if (ingredients.isNotEmpty()) {
            ingredients.joinToString("\n")
        } else {
            "No ingredients available"
        }
    }

    private fun getIngredients(meal: Meal): List<String> {
        val ingredients = mutableListOf<String>()
        for (i in 1..20) {
            try {
                val ingredientField = meal::class.java.getDeclaredField("ingredient$i")
                val measureField = meal::class.java.getDeclaredField("measure$i")

                // Make the fields accessible
                ingredientField.isAccessible = true
                measureField.isAccessible = true

                val ingredient = ingredientField.get(meal) as? String
                val measure = measureField.get(meal) as? String

                if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                    ingredients.add("$measure $ingredient")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ingredients
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
