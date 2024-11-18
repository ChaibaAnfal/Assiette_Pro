package com.example.assiette_pto.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.assiette_pto.databinding.FragmentMealDetailBinding
import com.example.assiette_pto.api_parameters.ApiClient
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
        val root: View = binding.root

        val mealId = requireArguments().getString("mealId") ?: return root
        fetchMealDetails(mealId)

        return root
    }

    private fun fetchMealDetails(mealId: String) {
        val call = ApiClient.mealApi.getMealDetails(mealId)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        // Set meal details
                        binding.tvMealName.text = meal.name
                        Picasso.get().load(meal.thumbnail).into(binding.ivMealImage)

                        // Add titles for Instructions and Ingredients
                        binding.tvInstructionsTitle.text = "Instructions"
                        binding.tvInstructions.text = meal.instructions

                        binding.tvIngredientsTitle.text = "Ingredients"
                        val ingredients = getIngredients(meal)
                        binding.tvIngredients.text = ingredients.joinToString("\n")
                    } else {
                        Toast.makeText(requireContext(), "Meal details not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load meal details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getIngredients(meal: Meal): List<String> {
        val ingredients = mutableListOf<String>()

        // Dynamically add non-null ingredients and measurements
        if (!meal.ingredient1.isNullOrEmpty()) ingredients.add("${meal.measure1} ${meal.ingredient1}")
        if (!meal.ingredient2.isNullOrEmpty()) ingredients.add("${meal.measure2} ${meal.ingredient2}")
        if (!meal.ingredient3.isNullOrEmpty()) ingredients.add("${meal.measure3} ${meal.ingredient3}")
        if (!meal.ingredient4.isNullOrEmpty()) ingredients.add("${meal.measure4} ${meal.ingredient4}")
        if (!meal.ingredient5.isNullOrEmpty()) ingredients.add("${meal.measure5} ${meal.ingredient5}")
        if (!meal.ingredient6.isNullOrEmpty()) ingredients.add("${meal.measure6} ${meal.ingredient6}")
        if (!meal.ingredient7.isNullOrEmpty()) ingredients.add("${meal.measure7} ${meal.ingredient7}")
        if (!meal.ingredient8.isNullOrEmpty()) ingredients.add("${meal.measure8} ${meal.ingredient8}")
        if (!meal.ingredient9.isNullOrEmpty()) ingredients.add("${meal.measure9} ${meal.ingredient9}")
        if (!meal.ingredient10.isNullOrEmpty()) ingredients.add("${meal.measure10} ${meal.ingredient10}")
        if (!meal.ingredient11.isNullOrEmpty()) ingredients.add("${meal.measure11} ${meal.ingredient11}")
        if (!meal.ingredient12.isNullOrEmpty()) ingredients.add("${meal.measure12} ${meal.ingredient12}")
        if (!meal.ingredient13.isNullOrEmpty()) ingredients.add("${meal.measure13} ${meal.ingredient13}")
        if (!meal.ingredient14.isNullOrEmpty()) ingredients.add("${meal.measure14} ${meal.ingredient14}")
        if (!meal.ingredient15.isNullOrEmpty()) ingredients.add("${meal.measure15} ${meal.ingredient15}")
        if (!meal.ingredient16.isNullOrEmpty()) ingredients.add("${meal.measure16} ${meal.ingredient16}")
        if (!meal.ingredient17.isNullOrEmpty()) ingredients.add("${meal.measure17} ${meal.ingredient17}")
        if (!meal.ingredient18.isNullOrEmpty()) ingredients.add("${meal.measure18} ${meal.ingredient18}")
        if (!meal.ingredient19.isNullOrEmpty()) ingredients.add("${meal.measure19} ${meal.ingredient19}")
        if (!meal.ingredient20.isNullOrEmpty()) ingredients.add("${meal.measure20} ${meal.ingredient20}")

        return ingredients
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
