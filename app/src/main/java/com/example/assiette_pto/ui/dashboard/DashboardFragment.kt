package com.example.assiette_pto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assiette_pto.R
import com.example.assiette_pto.adapters.CategoryAdapter
import com.example.assiette_pto.databinding.FragmentDashboardBinding
import com.example.assiette_pto.utils.ItemOffsetDecoration

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

        // Initialize RecyclerView with GridLayoutManager for a grid-style layout
        val rvCategories = binding.rvCategories
        rvCategories.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
        rvCategories.addItemDecoration(
            ItemOffsetDecoration(requireContext(), R.dimen.recycler_view_item_offset)
        )
        categoryAdapter = CategoryAdapter(emptyList()) { categoryName ->
            // Create a bundle with the category name
            val bundle = Bundle().apply {
                putString("categoryName", categoryName)
            }
            // Navigate to MealListFragment
            findNavController().navigate(R.id.mealListFragment, bundle)
        }
        rvCategories.adapter = categoryAdapter

        // Observe categories from ViewModel
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
