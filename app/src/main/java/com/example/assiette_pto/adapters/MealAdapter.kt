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

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealName: TextView = itemView.findViewById(R.id.tvMealName)
        private val mealThumbnail: ImageView = itemView.findViewById(R.id.ivMealThumbnail)
        private val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)

        fun bind(meal: Meal) {
            mealName.text = meal.name

            // Load meal thumbnail
            val thumbnailUrl = meal.thumbnail.takeIf { it.isNotEmpty() }
            Picasso.get()
                .load(thumbnailUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(mealThumbnail)

            // Query and update the favorite status
            FavoritesManager.isFavorite(meal) { isFavorite ->
                meal.isFavorite = isFavorite // Update the local meal object
                btnFavorite.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }

            // Handle favorite toggle
            btnFavorite.setOnClickListener {
                meal.isFavorite = !meal.isFavorite
                if (meal.isFavorite) {
                    FavoritesManager.addFavorite(meal)
                } else {
                    FavoritesManager.removeFavorite(meal)
                }
                btnFavorite.setImageResource(
                    if (meal.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }

            // Handle meal click
            itemView.setOnClickListener { onMealClick(meal) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    // Update the dataset
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMeals: List<Meal>?) {
        if (newMeals != null) {
            meals = newMeals.map { recipe ->
                Meal(
                    id = recipe.id,
                    name = recipe.name,
                    thumbnail = recipe.thumbnail,
                    instructions = recipe.instructions,
                    ingredient1 = recipe.ingredient1,
                    ingredient2 = recipe.ingredient2,
                    ingredient3 = recipe.ingredient3,
                    ingredient4 = recipe.ingredient4,
                    ingredient5 = recipe.ingredient5,
                    ingredient6 = recipe.ingredient6,
                    ingredient7 = recipe.ingredient7,
                    ingredient8 = recipe.ingredient8,
                    ingredient9 = recipe.ingredient9,
                    ingredient10 = recipe.ingredient10,
                    ingredient11 = recipe.ingredient11,
                    ingredient12 = recipe.ingredient12,
                    ingredient13 = recipe.ingredient13,
                    ingredient14 = recipe.ingredient14,
                    ingredient15 = recipe.ingredient15,
                    ingredient16 = recipe.ingredient16,
                    ingredient17 = recipe.ingredient17,
                    ingredient18 = recipe.ingredient18,
                    ingredient19 = recipe.ingredient19,
                    ingredient20 = recipe.ingredient20,
                    measure1 = recipe.measure1,
                    measure2 = recipe.measure2,
                    measure3 = recipe.measure3,
                    measure4 = recipe.measure4,
                    measure5 = recipe.measure5,
                    measure6 = recipe.measure6,
                    measure7 = recipe.measure7,
                    measure8 = recipe.measure8,
                    measure9 = recipe.measure9,
                    measure10 = recipe.measure10,
                    measure11 = recipe.measure11,
                    measure12 = recipe.measure12,
                    measure13 = recipe.measure13,
                    measure14 = recipe.measure14,
                    measure15 = recipe.measure15,
                    measure16 = recipe.measure16,
                    measure17 = recipe.measure17,
                    measure18 = recipe.measure18,
                    measure19 = recipe.measure19,
                    measure20 = recipe.measure20,
                    isFavorite = false // Adjust as needed
                )
            }
        }
        notifyDataSetChanged()
    }

}
