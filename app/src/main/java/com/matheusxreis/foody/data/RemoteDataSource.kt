package com.matheusxreis.foody.data

import com.matheusxreis.foody.data.network.FoodRecipesApi
import com.matheusxreis.foody.models.FoodJoke
import com.matheusxreis.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
)  {

    suspend fun getRecipes(queries:Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQueries:Map<String,String>):Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQueries)
    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}