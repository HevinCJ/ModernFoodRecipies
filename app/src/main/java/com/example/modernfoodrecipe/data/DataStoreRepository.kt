package com.example.modernfoodrecipe.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class  DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {


    private object preferenceKeys{
        val selectedmealtypeid = intPreferencesKey("mealtypeid")
        val selectedmealtype = stringPreferencesKey("mealtype")
        val selectediettypeid= intPreferencesKey("diettypeid")
        val selecteddiettype= stringPreferencesKey("diettype")
    }

   private val Context.datastore:DataStore<Preferences> by  preferencesDataStore("food_preferences")

    suspend fun savemealanddiettype(mealid:Int,mealtype:String,dietid:Int,diettype:String){
        context.datastore.edit {preferences->
           preferences[preferenceKeys.selecteddiettype]=diettype
            preferences[preferenceKeys.selectediettypeid]=dietid
            preferences[preferenceKeys.selectedmealtypeid]=mealid
            preferences[preferenceKeys.selectedmealtype]=mealtype
        }
    }

    val readMealAndDataTypes :Flow<mealanddietypes> = context.datastore.data

        .catch {
            exception->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }

        .map {
            preferences->
           val selecteddietype= preferences[preferenceKeys.selecteddiettype]  ?:DEFAULT_DIET_TYPE
            val selecteddietid=preferences[preferenceKeys.selectediettypeid]  ?:0
            val selectedmealid=preferences[preferenceKeys.selectedmealtypeid] ?:0
            val selectedmealtype=preferences[preferenceKeys.selectedmealtype] ?:DEFAULT_MEAL_TYPE
            mealanddietypes(selectedmealid,selectedmealtype,selecteddietid,selecteddietype)
        }


    data class mealanddietypes(val selectedmealid:Int,val selectedmealtype:String,val selecteddietid:Int,val selecteddietype:String)

}