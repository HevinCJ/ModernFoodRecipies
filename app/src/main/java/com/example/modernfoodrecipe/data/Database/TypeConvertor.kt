package com.example.modernfoodrecipe.data.Database

import androidx.room.TypeConverter
import com.example.modernfoodrecipe.models.FoodRecipes
import com.example.modernfoodrecipe.models.ResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertor {


    @TypeConverter
    fun foodRecipeToString(foodRecipes: FoodRecipes):String{
        return Gson().toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipes{
//        TypeToken.get(FoodRecipes::class.java)
        val listType = object : TypeToken<FoodRecipes>() {}.type
        return Gson().fromJson(data,listType)
    }


    @TypeConverter
    fun resultToString(result:ResultX):String{
        return Gson().toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String):ResultX{
        val listtype = object :TypeToken<ResultX>(){}.type
        return Gson().fromJson(data,listtype)
    }

}