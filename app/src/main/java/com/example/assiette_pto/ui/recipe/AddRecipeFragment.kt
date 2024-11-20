package com.example.assiette_pto.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.assiette_pto.databinding.FragmentAddRecipeBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)

        // Handle Save Recipe button click
        binding.btnSaveRecipe.setOnClickListener {
            saveRecipe()
        }

        return binding.root
    }

    private fun saveRecipe() {
        val recipeName = binding.etRecipeName.text.toString()
        val ingredients = binding.etIngredients.text.toString()
        val instructions = binding.etInstructions.text.toString()

        if (recipeName.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Save recipe to Firestore
        val recipe = hashMapOf(
            "name" to recipeName,
            "ingredients" to ingredients,
            "instructions" to instructions
        )
        FirebaseFirestore.getInstance().collection("recipes")
            .add(recipe)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Recipe added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to add recipe: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
