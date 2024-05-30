package com.example.recipefinder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipefinder.model.Meal

class RecipeAdapter(private val context: Context, private val meals: List<Meal>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val meal = meals[position]
        holder.recipeName.text = meal.strMeal
        Glide.with(context).load(meal.strMealThumb).into(holder.recipeImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("meal", meal)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = meals.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.textView_recipe_name)
        val recipeImage: ImageView = itemView.findViewById(R.id.imageView_recipe)
    }
}