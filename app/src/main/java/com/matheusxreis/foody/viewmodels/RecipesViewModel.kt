package com.matheusxreis.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.matheusxreis.foody.BuildConfig
import com.matheusxreis.foody.data.DataStoreRepository
import com.matheusxreis.foody.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application, private val dataStoreRepository:DataStoreRepository):AndroidViewModel(application){


    private var mealType = Constants.DEFAULT_MEAL_TYPE
    private var dietType = Constants.DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType:String, mealTypeId:Int, dietType: String, dietTypeId:Int) = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.saveMealAndDietType(
            mealType = mealType,
            mealTypeId = mealTypeId,
            dietType = dietType,
            dietTypeId = dietTypeId
        )
    }
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[Constants.QUERY_NUMBER] =  Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = BuildConfig.apiKey
        queries[Constants.QUERY_TYPE] = Constants.DEFAULT_MEAL_TYPE
        queries[Constants.QUERY_DIET] = Constants.DEFAULT_DIET_TYPE
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENT] = "true"

        return queries
    }

}