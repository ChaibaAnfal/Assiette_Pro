package com.example.assiette_pto.ui.meal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.databinding.FragmentMealListBinding
import com.example.assiette_pto.adapters.MealAdapter
import com.example.assiette_pto.api_parameters.ApiClient
import com.example.assiette_pto.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealListFragment : Fragment() {

    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Retrieve the category name from the arguments passed via the Bundle
        val categoryName = requireArguments().getString("categoryName") ?: "Unknown Category"
        binding.tvCategoryName.text = "Meals in $categoryName"

        // Initialize RecyclerView
        val rvMeals = binding.rvMeals
        rvMeals.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter(emptyList()) { meal ->
            Toast.makeText(requireContext(), "Clicked: ${meal.name}", Toast.LENGTH_SHORT).show()
        }
        rvMeals.adapter = mealAdapter

        // Fetch meals for the selected category
        fetchMealsByCategory(categoryName)

        return root
    }

    private fun fetchMealsByCategory(categoryName: String) {
        // Make the API call
        val call = ApiClient.mealApi.getMealsByCategory(categoryName)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals != null) {
                        mealAdapter.updateData(meals) // Update the adapter with fetched meals
                    } else {
                        Toast.makeText(requireContext(), "No meals found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("MealListFragment", "API Response Failed: ${response.message()}")
                    Toast.makeText(requireContext(), "Failed to load meals", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("MealListFragment", "API Call Failed: ${t.message}")
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
