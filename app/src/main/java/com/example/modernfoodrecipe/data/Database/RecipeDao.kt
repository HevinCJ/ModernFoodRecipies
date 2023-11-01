package com.example.modernfoodrecipe.data.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.modernfoodrecipe.data.Entity.FoodJokeEntity
import com.example.modernfoodrecipe.data.Entity.RecipeEntity
import com.example.modernfoodrecipe.data.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM Recipe_table ORDER BY id ASC ")
    fun getallRecipedata():Flow<List<RecipeEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertFavourite(favouriteEntity: FavouriteEntity)


     @Query("SELECT * FROM FAVOURITES_TABLE ORDER BY ID ASC")
      fun readFavouriteRecipe():Flow<List<FavouriteEntity>>

     @Delete
     suspend fun deletefavouriterecipe(favouriteEntity:FavouriteEntity)

     @Query("DELETE FROM FAVOURITES_TABLE")
     suspend fun deleteAll()


     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertFoodJoke(foodJoke: FoodJokeEntity)

     @Query("SELECT * FROM FOODJOKETABLE ORDER BY ID ASC")
     fun getallfoodjoke():Flow<List<FoodJokeEntity>>

}