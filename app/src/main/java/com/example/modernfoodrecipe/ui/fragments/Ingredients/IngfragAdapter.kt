package com.example.modernfoodrecipe.ui.fragments.Ingredients

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernfoodrecipe.ui.fragments.Recipes.RecepiesDiffUtil
import com.example.modernfoodrecipe.databinding.IngredientRowLayoutBinding
import com.example.modernfoodrecipe.models.ExtendedIngredient
import com.example.modernfoodrecipe.utils.Constants.Companion.BASE_IMAGE_URL

class IngfragAdapter:RecyclerView.Adapter<IngfragAdapter.MyViewHolder>() {

    var ingredients = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(IngredientRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = ingredients[position]
      with(holder){


          binding.txtviewtitle.text= currentitem.name.toString()
          binding.txtviewconsistency.text=currentitem.consistency
          binding.txtvieworginal.text=currentitem.original
          binding.imgviewingredient.load(BASE_IMAGE_URL+currentitem.image)

          Log.d("Image URL", "URL: ${currentitem.image}")
          binding.txtviewunitno.text=currentitem.amount.toString()
          binding.unit.text=currentitem.unit

      }


    }

    fun setingredientdata(ingredient: List<ExtendedIngredient>){

        val ingredientdiff = RecepiesDiffUtil(ingredients,ingredient)
        val ingredientdiffutil = DiffUtil.calculateDiff(ingredientdiff)
        this.ingredients=ingredient
        ingredientdiffutil.dispatchUpdatesTo(this)
    }
}