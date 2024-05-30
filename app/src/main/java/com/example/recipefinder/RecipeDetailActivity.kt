package com.example.recipefinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recipefinder.databinding.ActivityRecipeDetailBinding
import com.example.recipefinder.model.Meal

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meal = intent.getSerializableExtra("meal") as Meal
        binding.recipeName.text = meal.strMeal
        Glide.with(this).load(meal.strMealThumb).into(binding.recipeImage)
        binding.recipeInstructions.text = meal.strInstructions
    }
}