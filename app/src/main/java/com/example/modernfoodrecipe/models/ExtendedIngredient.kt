package com.example.modernfoodrecipe.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    @SerializedName("amount")
    var amount: Double?,
    @SerializedName("consistency")
    var consistency: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("meta")
    var meta: List<String?>,
    @SerializedName("name")
    var name: String?,
    @SerializedName("nameClean")
    var nameClean: String?,
    @SerializedName("original")
    var original: String?,
    @SerializedName("originalName")
    var originalName: String?,
    @SerializedName("unit")
    var unit: String?
):Parcelable