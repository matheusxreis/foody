package com.matheusxreis.foody.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
):Parcelable