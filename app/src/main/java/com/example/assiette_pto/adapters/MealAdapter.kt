package com.example.assiette_pto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assiette_pto.R
import com.example.assiette_pto.responses.Meal
import com.squareup.picasso.Picasso

class MealAdapter(
    private var meals: List<Meal>, // List of meals to display
    private val onMealClick: (Meal) -> Unit // Click listener for each meal
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    // ViewHolder class to hold references to the views in each item
    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealName: TextView = itemView.findViewById(R.id.tvMealName)
        val mealImage: ImageView = itemView.findViewById(R.id.ivMealImage)
    }

    // Create a new ViewHolder instance for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    // Bind data to the views in each item
    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealName.text = meal.name
        Picasso.get().load(meal.thumbnail).into(holder.mealImage)

        // Handle item click
        holder.itemView.setOnClickListener {
            onMealClick(meal) // Pass the clicked meal to the listener
        }
    }

    override fun getItemCount(): Int = meals.size

    // Update the data in the adapter dynamically
    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}
