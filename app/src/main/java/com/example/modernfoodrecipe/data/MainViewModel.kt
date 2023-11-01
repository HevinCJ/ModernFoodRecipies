package com.example.modernfoodrecipe.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modernfoodrecipe.data.Entity.FoodJokeEntity
import com.example.modernfoodrecipe.data.Entity.RecipeEntity
import com.example.modernfoodrecipe.data.Repository.Repository
import com.example.modernfoodrecipe.models.FoodJoke
import com.example.modernfoodrecipe.models.FoodRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor (application: Application,private val repository: Repository):AndroidViewModel(application) {



    /** Room Database*/

    var getalldata:LiveData<List<RecipeEntity>> = repository.localData.readDatabase().asLiveData()

    var readfavouriterecipes:LiveData<List<FavouriteEntity>> = repository.localData.readfavoriterecipes().asLiveData()

    var getfoodjoke:LiveData<List<FoodJokeEntity>> = repository.localData.getallfoodjoke().asLiveData()

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.localData.insertFoodJoke(foodJokeEntity)
    }

    fun insertRecepies(recipeEntity: RecipeEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.localData.insertRecpies(recipeEntity)
    }

    fun insertfavouriterecipes(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.localData.insertFavouriteRecipes(favouriteEntity)
    }

    fun deletefavouriteRecipes(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.localData.deleteFavouriteRecipes(favouriteEntity)
    }

    fun deleteall() = viewModelScope.launch(Dispatchers.IO){
        repository.localData.deleteall()
    }


    /** Retrofit*/
    var reciperesponse:MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()

    var searchreciperesponse:MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()

    var foodJokeresponse:MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()


    fun searchrecipies(queries: Map<String, String>) = viewModelScope.launch {
        searchrecipiessafecall(queries)
    }

    fun getrecipes(queries:Map<String,String>) = viewModelScope.launch {
        getrecipiessafecall(queries)
    }

    fun getFoodJoke(apiKey:String)=viewModelScope.launch {
        getFoodJokesafecall(apiKey)
    }


    private suspend fun searchrecipiessafecall(queries: Map<String, String>) {
        searchreciperesponse.value=NetworkResult.Loading()
        if (isNetworkAvailable()){
            try {
                val response = repository.remote.searchRecipies(queries)
                    searchreciperesponse.value = handleFoodResponseApi(response)

            }catch (e:Exception){
                searchreciperesponse.value=NetworkResult.Error("Recipies not found")
            }

        }else{
            searchreciperesponse.value=NetworkResult.Error(message = "No Internet connection")
        }
    }




    private suspend fun getrecipiessafecall(queries: Map<String, String>) {
        reciperesponse.value=NetworkResult.Loading()
        if (isNetworkAvailable()){
       try {
        val response = repository.remote.getrecepies(queries)
        reciperesponse.value = handleFoodResponseApi(response)

           val foodrecipe = reciperesponse.value!!.data
           if (foodrecipe!=null) {
               offlineCacheTheData(foodrecipe)
           }

        }catch (e:Exception){
            reciperesponse.value=NetworkResult.Error("Recipies not found")
        }

        }else{
            reciperesponse.value=NetworkResult.Error(message = "No Internet connection")
        }

    }

    private fun offlineCacheTheData(foodrecipe: FoodRecipes) {
        val recipeEntity = RecipeEntity(foodrecipe)
        insertRecepies(recipeEntity)
    }

    private fun handleFoodResponseApi(response: Response<FoodRecipes>): NetworkResult<FoodRecipes> {

        return when{
            response.message().toString().contains("timeout")->return NetworkResult.Error("Requested Network has timed out")
             response.code() == 402 -> return NetworkResult.Error("Api key limit has Overgone")
            response.body()?.results.isNullOrEmpty() ->return NetworkResult.Error("No Response found")
            response.isSuccessful ->{
                val foodrecipes = response.body()
                NetworkResult.Success(foodrecipes!!)
            }
            else -> return NetworkResult.Error( response.message())
        }
    }

    private suspend fun getFoodJokesafecall(apiKey: String) {
        foodJokeresponse.value=NetworkResult.Loading()
        if (isNetworkAvailable()){
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeresponse.value = handleFoodJokeResponseApi(response)


                val foodjoke = foodJokeresponse.value?.data
                if (foodjoke!=null){
                    offlinecachethefoodjoke(foodjoke)
                }

            }catch (e:Exception){
                foodJokeresponse.value=NetworkResult.Error("Recipies not found")
            }

        }else{
            foodJokeresponse.value=NetworkResult.Error(message = "No Internet connection")
        }
    }

    private fun offlinecachethefoodjoke(foodjoke: FoodJoke) {
        val foodjokeentity = FoodJokeEntity(foodjoke)
        insertFoodJoke(foodjokeentity)
    }

    private fun handleFoodJokeResponseApi(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        return when{
            response.message().toString().contains("timeout")->return NetworkResult.Error("Requested Network has timed out")
            response.code() == 402 -> return NetworkResult.Error("Api key limit has Overgone")
            response.isSuccessful ->{
                val foodrecipes = response.body()
                NetworkResult.Success(foodrecipes!!)
            }
            else -> return NetworkResult.Error( response.message())
        }
    }



    private fun isNetworkAvailable():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activenetwork = connectivityManager.activeNetwork ?: return false
        val capabalities = connectivityManager.getNetworkCapabilities(activenetwork) ?: return false


        return when{
            capabalities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabalities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabalities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }
}