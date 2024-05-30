package com.example.recipefinder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefinder.databinding.ActivityMainBinding
import com.example.recipefinder.model.MealResponse
import com.example.recipefinder.model.Meal
import com.example.recipefinder.network.MealApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mealApiService: MealApiService
    private lateinit var recipeAdapter: RecipeAdapter
    private var mealsList: MutableList<Meal> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            showAlertDialog()
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mealApiService = retrofit.create(MealApiService::class.java)

        binding.buttonSearch.setOnClickListener {
            search()
        }

        setupRecyclerView()
    }

    private fun showAlertDialog() {
        val instructions = """
            
            1. Make sure you have internet
            
            2. Search any food and get it's recipe
            
            3. Have fun! :)
            
            """.trimIndent()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome!")
            .setMessage(instructions)
        // Set a positive button and its click listener
        builder.setPositiveButton("OK") { dialog, _ ->
            // Do something when OK button is clicked
            dialog.dismiss() // Dismiss the dialog
        }

        // Create and show the AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(this, mealsList)
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewResults.adapter = recipeAdapter
    }

    private fun search() {
        val userT = binding.editTextSearch.text.toString()
        if (userT.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            return
        }

        mealApiService.searchMeals(userT).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful && response.body()?.meals != null) {
                    mealsList.clear()
                    mealsList.addAll(response.body()!!.meals!!)
                    recipeAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Sorry, $userT is not available", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}