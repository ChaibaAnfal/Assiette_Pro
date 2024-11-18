package com.example.assiette_pto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.adapters.CategoryAdapter
import com.example.assiette_pto.R
import com.example.assiette_pto.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RecyclerView
        val rvCategories = binding.rvCategories
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter = CategoryAdapter(emptyList()) { categoryName ->
            // Create a bundle with the category name
            val bundle = Bundle().apply {
                putString("categoryName", categoryName)
            }
            // Navigate to MealListFragment
            findNavController().navigate(R.id.mealListFragment, bundle)
        }
        rvCategories.adapter = categoryAdapter

        // Observe categories
        dashboardViewModel.categories.observe(viewLifecycleOwner) { categories ->
            if (categories != null) {
                categoryAdapter.updateData(categories)
            }
        }

        // Fetch categories
        dashboardViewModel.fetchCategories()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
