package com.example.modernfoodrecipe.ui.Mainactivity.fragments.Favourite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.modernfoodrecipe.data.FavouriteEntity

class FavouriteBindingAdapter {

    companion object{

        @BindingAdapter("viewVisiblity")
        @JvmStatic
        fun setDataAndVisiblility(view:View,favouriteEntity: List<FavouriteEntity>?){
            if (favouriteEntity.isNullOrEmpty()){
                when(view){
                    is TextView -> view.visibility = View.VISIBLE

                    is ImageView -> view.visibility = View.VISIBLE

                }
            }else{
                when(view){
                    is TextView -> view.visibility = View.INVISIBLE

                    is ImageView -> view.visibility = View.INVISIBLE

                }
            }
        }


    }
}