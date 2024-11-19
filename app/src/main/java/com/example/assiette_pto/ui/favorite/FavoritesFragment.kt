package com.example.assiette_pto.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assiette_pto.adapters.MealAdapter
import com.example.assiette_pto.databinding.FragmentFavoritesBinding
import com.example.assiette_pto.manager.FavoritesManager
import com.example.assiette_pto.responses.*

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        // Initialize RecyclerView
        mealAdapter = MealAdapter(FavoritesManager.getFavorites()) { meal ->
            // Handle click on a meal (e.g., navigate to details)
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = mealAdapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list of favorites
        mealAdapter.updateData(FavoritesManager.getFavorites())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
