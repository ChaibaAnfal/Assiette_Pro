package com.example.assiette_pto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assiette_pto.R
import com.example.assiette_pto.adapters.CategoryAdapter
import com.example.assiette_pto.databinding.FragmentDashboardBinding
import com.example.assiette_pto.responses.Category

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    private var originalCategories: List<Category> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        val rvCategories = binding.rvCategories
        rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        categoryAdapter = CategoryAdapter(emptyList()) { categoryName ->
            // Navigate to meals in the selected category
            val bundle = Bundle().apply {
                putString("categoryName", categoryName)
            }
            findNavController().navigate(R.id.mealListFragment, bundle)
        }
        rvCategories.adapter = categoryAdapter

        // Observe categories
        dashboardViewModel.categories.observe(viewLifecycleOwner) { categories ->
            if (categories != null) {
                originalCategories = categories // Save the full category list
                categoryAdapter.updateData(categories)
            }
        }

        // Fetch categories
        dashboardViewModel.fetchCategories()

        // Set up search functionality
        setupSearch()

        return binding.root
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterCategories(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCategories(newText)
                return true
            }
        })
    }

    private fun filterCategories(query: String?) {
        if (query.isNullOrEmpty()) {
            categoryAdapter.updateData(originalCategories) // Show all categories
        } else {
            val filteredCategories = originalCategories.filter {
                it.name.contains(query, ignoreCase = true)
            }
            categoryAdapter.updateData(filteredCategories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
