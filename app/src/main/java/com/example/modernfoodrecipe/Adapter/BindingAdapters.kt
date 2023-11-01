package com.example.modernfoodrecipe.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import coil.request.CachePolicy
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.models.ResultX
import com.example.modernfoodrecipe.ui.Mainactivity.fragments.Recipes.Recipe_fragmentDirections
import org.jsoup.Jsoup


class BindingAdapters{

    companion object{

        @BindingAdapter("android:onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(Recipelayout:ConstraintLayout,resultX:ResultX){
            Recipelayout.setOnClickListener{
                val action = Recipe_fragmentDirections.actionRecipeFragmentToDetailsActivity(resultX)
                Recipelayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("isvegan")
        @JvmStatic
        fun isvegan(view:View,vegan:Boolean){
            if (vegan){
                when(view){
                    is TextView ->view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    is ImageView ->view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                }
            }else{
                when(view){
                    is TextView ->view.setTextColor(ContextCompat.getColor(view.context, R.color.mediumGray))
                    is ImageView ->view.setColorFilter(ContextCompat.getColor(view.context, R.color.mediumGray))
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(view: ImageView,url:String){
            view.load(url){
                crossfade(600)
                diskCachePolicy(CachePolicy.ENABLED)
                memoryCachePolicy(CachePolicy.ENABLED)
                networkCachePolicy(CachePolicy.ENABLED)

            }

        }


        @BindingAdapter("parseHtmltoString")
        @JvmStatic
        fun parseHtmltoString(textView: TextView,description:String){
            if (description!=null){
                textView.text = Jsoup.parse(description).text()
            }
        }



        @BindingAdapter("parseIntToString")
        @JvmStatic
        fun parseIntToString(textView: TextView,int: Int){
                textView.text = int.toString()
        }
    }
}