package com.example.modernfoodrecipe.ui.fragments.Recipes

import androidx.recyclerview.widget.DiffUtil

class RecepiesDiffUtil<T>(private var oldlist:List<T>, private var newlist:List<T>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
      return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldlist[oldItemPosition] === newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newlist[newItemPosition]
    }


}