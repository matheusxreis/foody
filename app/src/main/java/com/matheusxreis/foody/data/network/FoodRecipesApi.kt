package com.matheusxreis.foody.data.network

import com.matheusxreis.foody.models.FoodJoke
import com.matheusxreis.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String> // -> @QueryMap allows that I define my queries inside a Map when call the function
    ):Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String> // -> @QueryMap allows that I define my queries inside a Map when call the function
    ): Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey: String
    ):Response<FoodJoke>
}