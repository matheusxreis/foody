package com.matheusxreis.foody.utils

import com.matheusxreis.foody.BuildConfig

class Constants {

    companion object {
        const val API_KEY= BuildConfig.apiKey
        const val BASE_URL="https://api.spoonacular.com"

        // API Query keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENT = "fillIngredient"


        // room

        const val DATABASE_NAME="recipes_database"
        const val RECIPES_TABLE ="recipes_table"
    }

}