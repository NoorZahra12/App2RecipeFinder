package com.example.recipefinder.model

import java.io.Serializable

data class MealResponse(val meals: List<Meal>?)
data class Meal(val strMeal: String, val strInstructions: String, val strMealThumb: String) : Serializable