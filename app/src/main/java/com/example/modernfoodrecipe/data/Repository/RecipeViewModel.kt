package com.example.modernfoodrecipe.data.Repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernfoodrecipe.data.DataStoreRepository
import com.example.modernfoodrecipe.utils.Constants.Companion.API_KEY
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_API_KEY
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_DIET
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_NUMBER
import com.example.modernfoodrecipe.utils.Constants.Companion.QUERY_TYPE
import com.example.modernfoodrecipe.utils.Constants.Companion.SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(application: Application,private val dataStoreRepository: DataStoreRepository):AndroidViewModel(application) {


   private var mealtype = DEFAULT_MEAL_TYPE
    private var diettype = DEFAULT_DIET_TYPE

    var networkstatus:Boolean = false

    val readmealanddiettypes = dataStoreRepository.readMealAndDataTypes


    fun  savemealdiettypes(mealid:Int,mealtype:String,dietid:Int,diettype:String) = viewModelScope.launch(Dispatchers.IO) {
       dataStoreRepository.savemealanddiettype(mealid,mealtype,dietid, diettype)
    }


     fun queries():Map<String,String>{

         viewModelScope.launch {
             readmealanddiettypes.collect{value->
                 mealtype=value.selectedmealtype
                 diettype=value.selecteddietype
             }
         }

        val queries:HashMap<String,String> = HashMap()


         queries[QUERY_NUMBER] = "50"
         queries[QUERY_API_KEY] =API_KEY
        queries[QUERY_TYPE] = mealtype
        queries[QUERY_DIET] = diettype
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }


    fun stringToQuery(SearchQuery:String):HashMap<String,String>{

        val queries:HashMap<String,String> = HashMap()

        queries[SEARCH_QUERY]= SearchQuery.lowercase(Locale.ROOT)
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] =API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun shownetworkstatus(){
        when(networkstatus){
            false->Toast.makeText(getApplication(),"No Internet connection",Toast.LENGTH_SHORT).show()

            else -> {}
        }
    }



}