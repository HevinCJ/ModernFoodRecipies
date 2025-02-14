package com.example.modernfoodrecipe.ui.fragments.Recipes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.modernfoodrecipe.data.Entity.RecipeEntity
import com.example.modernfoodrecipe.utils.NetworkResult
import com.example.modernfoodrecipe.models.FoodRecipes

class Recipe_Fragment_Binding_Adapter {

    companion object{

        @BindingAdapter("readapiimageresponse","readimagedatabaseresponse", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(imageView: ImageView, apiresponse: NetworkResult<FoodRecipes>?, database:List<RecipeEntity>?){
            if (apiresponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }


        @BindingAdapter("readapitextresponse","readtextdatabaseresponse", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(textView: TextView, apiresponse: NetworkResult<FoodRecipes>?, database:List<RecipeEntity>?){
            if (apiresponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text=apiresponse.message
            } else {
                textView.visibility = View.INVISIBLE
            }

        }

    }

}