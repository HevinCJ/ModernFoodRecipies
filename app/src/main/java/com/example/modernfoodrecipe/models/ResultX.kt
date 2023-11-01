package com.example.modernfoodrecipe.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize


@Parcelize
data class ResultX(
    @SerializedName("aggregateLikes")
    var aggregateLikes: Int,
    @SerializedName("cheap")
    var cheap: Boolean,
    @SerializedName("cookingMinutes")
    var cookingMinutes: Int,
    @SerializedName("creditsText")
    var creditsText: String,
    @SerializedName("cuisines")
    var cuisines: List<String>,
    @SerializedName("dairyFree")
    var dairyFree: Boolean,
    @SerializedName("diets")
    var diets: List<String>,
    @SerializedName("dishTypes")
    var dishTypes: List<String>,
    @SerializedName("extendedIngredients")
    var extendedIngredients: @RawValue List<ExtendedIngredient>,
    @SerializedName("gaps")
    var gaps: String,
    @SerializedName("glutenFree")
    var glutenFree: Boolean,
    @SerializedName("healthScore")
    var healthScore: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    var image: String,
    @SerializedName("imageType")
    var imageType: String,
    @SerializedName("likes")
    var likes: Int,
    @SerializedName("lowFodmap")
    var lowFodmap: Boolean,
    @SerializedName("missedIngredientCount")
    var missedIngredientCount: Int,
    @SerializedName("occasions")
    var occasions: List<String>,
    @SerializedName("preparationMinutes")
    var preparationMinutes: Int,
    @SerializedName("pricePerServing")
    var pricePerServing: Double,
    @SerializedName("readyInMinutes")
    var readyInMinutes: Int,
    @SerializedName("servings")
    var servings: Int,
    @SerializedName("sourceName")
    var sourceName: String,
    @SerializedName("sourceUrl")
    var sourceUrl: String,
    @SerializedName("spoonacularSourceUrl")
    var spoonacularSourceUrl: String,
    @SerializedName("summary")
    var summary: String,
    @SerializedName("sustainable")
    var sustainable: Boolean,
    @SerializedName("title")
    var title: String,
    @SerializedName("usedIngredientCount")
    var usedIngredientCount: Int,
    @SerializedName("usedIngredients")
    var usedIngredients: @RawValue List<Any>,
    @SerializedName("vegan")
    var vegan: Boolean,
    @SerializedName("vegetarian")
    var vegetarian: Boolean,
    @SerializedName("veryHealthy")
    var veryHealthy: Boolean,
    @SerializedName("veryPopular")
    var veryPopular: Boolean,
    @SerializedName("weightWatcherSmartPoints")
    var weightWatcherSmartPoints: Int
):Parcelable