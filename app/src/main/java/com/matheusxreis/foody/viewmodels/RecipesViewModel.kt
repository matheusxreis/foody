package com.matheusxreis.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.matheusxreis.foody.BuildConfig
import com.matheusxreis.foody.utils.Constants

class RecipesViewModel(application: Application):AndroidViewModel(application){


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[Constants.QUERY_NUMBER] =  Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = BuildConfig.apiKey
        queries[Constants.QUERY_TYPE] = Constants.DEFAULT_MEAL_TYPE
        queries[Constants.QUERY_DIET] = Constants.DEFAULT_DIET_TYPE
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENT] = "true"

        return queries
    }

}