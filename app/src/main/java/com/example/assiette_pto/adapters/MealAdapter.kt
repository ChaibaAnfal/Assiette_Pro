package com.example.assiette_pto.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assiette_pto.R
import com.example.assiette_pto.manager.FavoritesManager
import com.example.assiette_pto.responses.Meal
import com.squareup.picasso.Picasso

class MealAdapter(
    private var meals: List<Meal>,
    private val onMealClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    // ViewHolder class
    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealName: TextView = itemView.findViewById(R.id.tvMealName)
        private val mealThumbnail: ImageView = itemView.findViewById(R.id.ivMealThumbnail)
        private val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)

        fun bind(meal: Meal) {
            mealName.text = meal.name

            // Check if the thumbnail is not empty or null
            if (!meal.thumbnail.isNullOrEmpty()) {
                Picasso.get()
                    .load(meal.thumbnail)
                    .placeholder(R.drawable.ic_placeholder) // Use a placeholder while loading
                    .error(R.drawable.ic_placeholder)       // Use a placeholder if loading fails
                    .into(mealThumbnail)
            } else {
                // Set a placeholder if the URL is empty or null
                mealThumbnail.setImageResource(R.drawable.ic_placeholder)
            }

            // Check if meal is favorite and update icon
            FavoritesManager.isFavorite(meal) { isFavorite ->
                btnFavorite.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }

            // Handle favorite toggle
            btnFavorite.setOnClickListener {
                FavoritesManager.isFavorite(meal) { isFavorite ->
                    if (isFavorite) {
                        FavoritesManager.removeFavorite(meal)
                    } else {
                        FavoritesManager.addFavorite(meal)
                    }
                    // Update the icon
                    FavoritesManager.isFavorite(meal) { updatedIsFavorite ->
                        btnFavorite.setImageResource(
                            if (updatedIsFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                        )
                    }
                }
            }

            // Handle meal click
            itemView.setOnClickListener { onMealClick(meal) }
        }

    }

    // Create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    // Return the size of the dataset
    override fun getItemCount(): Int = meals.size

    // Update the dataset
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}
