package com.example.modernfoodrecipe.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfoodrecipe.RecepiesDiffUtil
import com.example.modernfoodrecipe.databinding.RecipeRcViewBinding
import com.example.modernfoodrecipe.models.FoodRecipes
import com.example.modernfoodrecipe.models.ResultX

class RecipesAdapter:RecyclerView.Adapter<RecipesAdapter.myViewholder>() {

    var recipies = emptyList<ResultX>()



    class myViewholder(private val binding:RecipeRcViewBinding):RecyclerView.ViewHolder(binding.root) {


        fun Bind(result:ResultX){
            binding.resultbnd=result
            binding.executePendingBindings()

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewholder {
        return myViewholder(RecipeRcViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = recipies.size

    override fun onBindViewHolder(holder: myViewholder, position: Int) {
        val currentitem = recipies[position]
        holder.Bind(currentitem)
    }

    fun setdata(data: FoodRecipes){
        val recepiesDiffUtil = RecepiesDiffUtil(recipies,data.results)
        val diffUtilresult = DiffUtil.calculateDiff(recepiesDiffUtil)
        diffUtilresult.dispatchUpdatesTo(this)
        recipies= data.results
    }




}