package com.matheusxreis.foody.models


import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("measures")
    val name: String,
    @SerializedName("nameClean")
    val original: String,
    @SerializedName("unit")
    val unit: String
)