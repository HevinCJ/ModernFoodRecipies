package com.example.modernfoodrecipe.data.Network

import com.example.modernfoodrecipe.models.FoodJoke
import com.example.modernfoodrecipe.models.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecepiesApi {


        @GET("recipes/complexSearch")
        suspend fun getRecipes(
            @QueryMap queries: Map<String,String>
        ):Response<FoodRecipes>


    @GET("recipes/complexSearch")
    suspend fun searchRecipies(
        @QueryMap searchquery: Map<String,String>
    ):Response<FoodRecipes>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
       @Query("apiKey") apiKey:String
    ):Response<FoodJoke>

    }

