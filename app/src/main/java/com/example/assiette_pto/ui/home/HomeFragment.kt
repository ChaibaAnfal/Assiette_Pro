package com.example.assiette_pto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.adapters.MealAdapter
import com.example.assiette_pto.api_parameters.ApiClient
import com.example.assiette_pto.databinding.FragmentHomeBinding
import com.example.assiette_pto.responses.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        val rvMeals = binding.rvMeals
        rvMeals.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter(emptyList()) { meal ->
            Toast.makeText(requireContext(), "Clicked: ${meal.name}", Toast.LENGTH_SHORT).show()
        }
        rvMeals.adapter = mealAdapter

        // Set up SearchView
        setupSearch()

        // Observe text from ViewModel for demonstration
        homeViewModel.text.observe(viewLifecycleOwner) {
            binding.tvTitle.text = it
        }

        return root
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchMeals(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Handle live search here
                return true
            }
        })
    }

    private fun searchMeals(query: String) {
        val call = ApiClient.mealApi.searchMeals(query)
        call.enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals != null && meals.isNotEmpty()) {
                        mealAdapter.updateData(meals)
                    } else {
                        Toast.makeText(requireContext(), "No meals found for \"$query\"", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch meals", Toast.LENGTH_SHORT).show()
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
