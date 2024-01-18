package com.example.modernfoodrecipe.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modernfoodrecipe.models.FoodRecipes

@Entity(tableName = "Recipe_table")
data class RecipeEntity(
    val foodRecipes: FoodRecipes
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int= 0
}