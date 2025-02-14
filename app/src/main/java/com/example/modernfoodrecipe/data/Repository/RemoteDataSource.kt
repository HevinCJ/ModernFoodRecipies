package com.example.modernfoodrecipe.data.Repository

import com.example.modernfoodrecipe.data.Network.FoodRecepiesApi
import com.example.modernfoodrecipe.models.FoodJoke
import com.example.modernfoodrecipe.models.FoodRecipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor( private var foodRecepiesApi: FoodRecepiesApi) {

    suspend fun getrecepies(queries:Map<String,String>):Response<FoodRecipes>{
        return foodRecepiesApi.getRecipes(queries)

    }

    suspend fun searchRecipies(queries:Map<String,String>):Response<FoodRecipes>{
        return foodRecepiesApi.searchRecipies(queries)

    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke>{
        return foodRecepiesApi.getFoodJoke(apiKey)
    }
}