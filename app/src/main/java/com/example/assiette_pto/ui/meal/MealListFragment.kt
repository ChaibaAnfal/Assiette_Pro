package com.example.assiette_pto.ui.meal

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.R
import com.example.assiette_pto.adapters.MealAdapter
import com.example.assiette_pto.databinding.FragmentMealListBinding
import com.example.assiette_pto.api_parameters.ApiClient
import com.example.assiette_pto.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealListFragment : Fragment() {

    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealAdapter: MealAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val categoryName = requireArguments().getString("categoryName") ?: "Unknown Category"
        binding.tvCategoryName.text = "Meals in $categoryName"

        val rvMeals = binding.rvMeals
        rvMeals.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter(emptyList()) { meal ->
            val bundle = Bundle().apply {
                putString("mealId", meal.id)
            }
            findNavController().navigate(R.id.mealDetailFragment, bundle)
        }
        rvMeals.adapter = mealAdapter

        fetchMealsByCategory(categoryName)

        return root
    }

    private fun fetchMealsByCategory(categoryName: String) {
        val call = ApiClient.mealApi.getMealsByCategory(categoryName)

        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals != null) {
                        mealAdapter.updateData(meals)
                    } else {
                        Toast.makeText(requireContext(), "No meals found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load meals", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
