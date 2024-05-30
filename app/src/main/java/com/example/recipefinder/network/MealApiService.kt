package com.example.recipefinder.network

import com.example.recipefinder.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    fun searchMeals(@Query("s") searchText: String): Call<MealResponse>
}
