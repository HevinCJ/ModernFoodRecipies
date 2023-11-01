package com.example.modernfoodrecipe.data.Entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modernfoodrecipe.models.FoodJoke

@Entity(tableName = "foodjoketable")
data class FoodJokeEntity(
    @Embedded
    var foodJoke:FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
   var id:Int = 0
}