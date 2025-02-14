package com.example.modernfoodrecipe.data.Repository

import com.example.modernfoodrecipe.data.Database.RecipeDao
import com.example.modernfoodrecipe.data.Entity.FavouriteEntity
import com.example.modernfoodrecipe.data.Entity.FoodJokeEntity
import com.example.modernfoodrecipe.data.Entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipeDao: RecipeDao) {

    fun readDatabase():Flow<List<RecipeEntity>> = recipeDao.getallRecipedata()

    fun readfavoriterecipes():Flow<List<FavouriteEntity>> = recipeDao.readFavouriteRecipe()

    suspend fun insertRecpies(recipeEntity: RecipeEntity) = recipeDao.insertRecipe(recipeEntity)

    suspend fun insertFavouriteRecipes(favouriteEntity: FavouriteEntity) = recipeDao.insertFavourite(favouriteEntity)

    suspend fun deleteFavouriteRecipes(favouriteEntity: FavouriteEntity) =recipeDao.deletefavouriterecipe(favouriteEntity)

    suspend fun deleteall() = recipeDao.deleteAll()

    fun getallfoodjoke():Flow<List<FoodJokeEntity>> = recipeDao.getallfoodjoke()

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = recipeDao.insertFoodJoke(foodJokeEntity)

}