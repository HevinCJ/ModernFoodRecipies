package com.example.modernfoodrecipe.ui.Mainactivity.fragments.FoodJoke

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.modernfoodrecipe.data.Entity.FoodJokeEntity
import com.example.modernfoodrecipe.data.NetworkResult
import com.example.modernfoodrecipe.models.FoodJoke
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object{

        @BindingAdapter("readapiresponsefk","readdatabasefk", requireAll = true)
        @JvmStatic
        fun setCardViewAndProgressBarVisibility(view:View,apiresponse:NetworkResult<FoodJoke>?,database:List<FoodJokeEntity>?) {
            when(apiresponse){

                is NetworkResult.Loading ->{
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE

                        }

                        is MaterialCardView ->{
                            view.visibility = View.INVISIBLE
                        }

                    }
                }

                is NetworkResult.Success ->{
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE

                        }

                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                        }

                    }
                }

                is NetworkResult.Error ->{
                    when(view){
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE

                        }

                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                            if (database!=null){
                                if (database.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }

                        }

                    }
                }

                else -> {}
            }
        }

        @BindingAdapter("readApiResponse4", "readDatabase4", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            if(database != null){
                if(database.isEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }


    }

}