package com.example.assiette_pto.adapters

import android.annotation.SuppressLint
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
    private var meals: List<Meal>,
    private val onMealClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealName: TextView = itemView.findViewById(R.id.tvMealName)
        val mealThumbnail: ImageView = itemView.findViewById(R.id.ivMealImage)

        fun bind(meal: Meal) {
            mealName.text = meal.name
            Picasso.get().load(meal.thumbnail).into(mealThumbnail)
            itemView.setOnClickListener { onMealClick(meal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}

