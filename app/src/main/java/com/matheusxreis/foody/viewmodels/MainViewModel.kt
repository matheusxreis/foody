package com.matheusxreis.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.matheusxreis.foody.data.Repository
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import com.matheusxreis.foody.data.database.entities.FoodJokeEntity
import com.matheusxreis.foody.data.database.entities.RecipesEntity
import com.matheusxreis.foody.models.FoodJoke
import com.matheusxreis.foody.models.FoodRecipe
import com.matheusxreis.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application):AndroidViewModel(application){

    // room **

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavoriteRecipe: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.local.insertRecipes(recipesEntity)
    }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) = viewModelScope.launch (Dispatchers.IO) {
        repository.local.insertFavoriteRecipe(favoritesEntity)
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.local.insertFoodJoke(foodJokeEntity)
    }
    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) = viewModelScope.launch (Dispatchers.IO) {
        repository.local.deleteFavoriteRecipe(favoritesEntity)
    }
    fun deleteAllFavoriteRecipes() = viewModelScope.launch (Dispatchers.IO) {
        repository.local.deleteAllFavoriteRecipes()
    }



    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJoke = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJoke)
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }



    // retrofit ***
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun searchRecipes(searchQueries:Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQueries)
    }

    fun getRecipes(queries:Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun getFoodJoke(apiKey:String)= viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.getRecipes(queries);
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data

                if(foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }
            }catch(e:Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found")
            }

        }else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }
    private suspend fun searchRecipesSafeCall(searchQueries: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.searchRecipes(searchQueries);
                searchedRecipesResponse.value = handleFoodRecipesResponse(response)

            }catch(e:Exception){
                searchedRecipesResponse.value = NetworkResult.Error("Recipes not found")
            }

        }else {
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }


    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.getFoodJoke(apiKey);
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if(foodJoke != null){
                    offlineCacheFoodJoke(foodJoke)
                }
            }catch(e:Exception){
                foodJokeResponse.value = NetworkResult.Error("Recipes not found")
            }

        }else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() === 402 -> {
                return NetworkResult.Error("API Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }


    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() === 402 -> {
                 NetworkResult.Error("API Key Limited")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                 NetworkResult.Success(foodJoke!!)
            }
            else -> {
                 NetworkResult.Error(response.message())
            }

        }
    }


    private fun hasInternetConnection():Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activityNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activityNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}