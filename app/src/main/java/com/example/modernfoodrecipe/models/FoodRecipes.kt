package com.example.modernfoodrecipe.models


import com.google.gson.annotations.SerializedName


data class FoodRecipes(
    @SerializedName("number")
    var number: Int,
    @SerializedName("offset")
    var offset: Int,
    @SerializedName("results")
    var results: List<ResultX>,
    @SerializedName("totalResults")
    var totalResults: Int
)