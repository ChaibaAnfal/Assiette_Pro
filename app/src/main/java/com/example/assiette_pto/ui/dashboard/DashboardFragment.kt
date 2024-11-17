package com.example.assiette_pto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.adapters.CategoryAdapter
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

        // Set up RecyclerView
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter = CategoryAdapter(emptyList())
        binding.rvCategories.adapter = categoryAdapter

        // Observe the categories LiveData
        dashboardViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateData(categories) // Update adapter when data changes
        }

        // Observe the text LiveData for status messages
        dashboardViewModel.text.observe(viewLifecycleOwner) { message ->
            binding.textDashboard.text = message
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
