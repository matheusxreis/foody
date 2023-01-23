package com.matheusxreis.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.foody.models.FoodRecipe
import com.matheusxreis.foody.utils.Constants

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)  // -> this table will have just one row
    var id:Int = 0
}