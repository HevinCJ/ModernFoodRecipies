package com.example.modernfoodrecipe.models


import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    var text: String?
)