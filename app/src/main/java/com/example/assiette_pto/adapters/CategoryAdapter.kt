package com.example.assiette_pto.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assiette_pto.R
import com.example.assiette_pto.responses.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(private var categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val categoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        Picasso.get().load(category.thumbnail).into(holder.categoryImage)
    }

    override fun getItemCount(): Int = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}
