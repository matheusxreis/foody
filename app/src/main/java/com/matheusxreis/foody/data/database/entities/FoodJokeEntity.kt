package com.matheusxreis.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.foody.models.FoodJoke
import com.matheusxreis.foody.utils.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0


}