package com.example.modernfoodrecipe.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.modernfoodrecipe.data.Entity.FoodJokeEntity
import com.example.modernfoodrecipe.data.Entity.RecipeEntity
import com.example.modernfoodrecipe.data.Entity.FavouriteEntity

@Database(entities =[RecipeEntity::class, FavouriteEntity::class,FoodJokeEntity::class], version = 1, exportSchema = false)


@TypeConverters(TypeConvertor::class)
abstract class RecipeDatabase:RoomDatabase() {

    abstract fun RecipeDao():RecipeDao

}