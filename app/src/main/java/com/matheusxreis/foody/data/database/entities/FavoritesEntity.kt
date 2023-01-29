package com.matheusxreis.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.foody.utils.Constants
import com.matheusxreis.foody.models.Result


@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result: Result
)