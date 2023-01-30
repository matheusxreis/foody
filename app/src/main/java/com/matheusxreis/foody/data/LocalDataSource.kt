package com.matheusxreis.foody.data

import com.matheusxreis.foody.data.database.RecipesDao
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import com.matheusxreis.foody.data.database.entities.FoodJokeEntity
import com.matheusxreis.foody.data.database.entities.RecipesEntity
import com.matheusxreis.foody.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }
    fun readFavoriteRecipes():Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }
    fun readFoodJoke(): Flow<List<FoodJoke>> {
        return recipesDao.readFoodJoke()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }
    suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }

}