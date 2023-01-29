package com.matheusxreis.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.foody.models.FoodRecipe
import com.matheusxreis.foody.utils.Constants

// this table of my database has just 2 columns, one is the id
// and other is the FoodRecipe value converted in string
// by the type converters
// because the id is not autoGenerate, the database has always
// just one row, with the more updated value

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)  // -> this table will have just one row
    var id:Int = 0
}