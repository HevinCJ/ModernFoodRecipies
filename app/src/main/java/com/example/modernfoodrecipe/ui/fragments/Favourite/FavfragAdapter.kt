package com.example.modernfoodrecipe.ui.fragments.Favourite

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.ui.fragments.Recipes.RecepiesDiffUtil
import com.example.modernfoodrecipe.data.Entity.FavouriteEntity
import com.example.modernfoodrecipe.viewmodels.MainViewModel
import com.example.modernfoodrecipe.databinding.FragmentRcViewBinding
import com.google.android.material.snackbar.Snackbar

class FavfragAdapter(private val requireactivity:FragmentActivity, private val mainViewModel: MainViewModel): RecyclerView.Adapter<FavfragAdapter.myViewHolder>(),ActionMode.Callback {

    private var favourites = emptyList<FavouriteEntity>()

    private var multipleselection  = false
    private var selectedRecipes = arrayListOf<FavouriteEntity>()
    private var viewHolders = arrayListOf<myViewHolder>()
    private lateinit var actionMode: ActionMode
    private var rootView:View?=null




    class myViewHolder(val binding: FragmentRcViewBinding): RecyclerView.ViewHolder(binding.root) {

            fun bind(currentitem: FavouriteEntity) {
                binding.favouriteEntity = currentitem
                binding.executePendingBindings()

            }


    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            return myViewHolder(FragmentRcViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

        override fun getItemCount(): Int = favourites.size


        override fun onBindViewHolder(holder: myViewHolder, position: Int) {
            viewHolders.add(holder)
            val currentitem = favourites[position]
            rootView = holder.itemView.rootView
            holder.bind(currentitem)

            with(holder){

                binding.favouriteRcLayout.setOnClickListener {
                    if (multipleselection){
                       applySelection(holder,currentitem)
                    }else{
                        val action = Favourite_fragmentDirections.actionFavouriteFragmentToDetailsActivity(currentitem.result)
                        binding.favouriteRcLayout.findNavController().navigate(action)
                    }

                }

                binding.favouriteRcLayout.setOnLongClickListener {
                    if (!multipleselection){
                        multipleselection = true
                        requireactivity.startActionMode(this@FavfragAdapter)
                        applySelection(holder,currentitem)
                        true
                    }else{
                        multipleselection = false
                        false
                    }


                }
            }


        }

    override fun onCreateActionMode(actionmode: ActionMode?,menu: Menu?): Boolean {
        this.actionMode = actionmode!!
        actionmode.menuInflater?.inflate(R.menu.favourite_action_mode,menu)
        return true
    }

    override fun onPrepareActionMode(actionmode: ActionMode?,menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionmode: ActionMode?,menu: MenuItem?): Boolean {
        when(menu?.itemId){
            R.id.deletefavouriteitem -> {
                showsnackbar("${selectedRecipes.size} Items Deleted")

                val iterator = selectedRecipes.iterator()
                while (iterator.hasNext()) {
                    val recipe = iterator.next()
                    mainViewModel.deletefavouriteRecipes(recipe)
                    iterator.remove()
                }

            }

        }

        multipleselection = false
        actionmode?.finish()
        selectedRecipes.clear()

        return true
    }



    override fun onDestroyActionMode(actionmode: ActionMode?) {


        viewHolders.forEach { holder ->
            changeFavouriteBackgroundStyle(holder,R.color.white,R.color.mediumGray)
        }
    }

    fun setdata(data: List<FavouriteEntity>) {
        val recepiesDiffUtil = RecepiesDiffUtil(favourites, data)
        val diffUtilresult = DiffUtil.calculateDiff(recepiesDiffUtil)
        diffUtilresult.dispatchUpdatesTo(this)
        favourites = data
    }

    private fun changeFavouriteBackgroundStyle(holder: myViewHolder, backgroundcolor:Int, strokecolor:Int){
        with(holder){
            binding.favouriteRcLayout.setBackgroundColor(ContextCompat.getColor(requireactivity,backgroundcolor))
            binding.cardviewFavourite.strokeColor = ContextCompat.getColor(requireactivity,strokecolor)
        }
    }

    private fun applySelection(holder: myViewHolder, currentitem: FavouriteEntity){
        if (selectedRecipes.contains(currentitem)){
            selectedRecipes.remove(currentitem)
            changeFavouriteBackgroundStyle(holder,R.color.white,R.color.mediumGray)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentitem)
            changeFavouriteBackgroundStyle(holder,R.color.mediumGray,R.color.colorPrimaryDark)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){

            0 ->actionMode.finish()

            1 ->actionMode.title = "${selectedRecipes.size} Items Selected"

            else -> actionMode.title = "${selectedRecipes.size} Items Selected"

        }
    }

    private fun showsnackbar(Message: String) {
        Snackbar.make(rootView!!,Message,Snackbar.LENGTH_SHORT).show()
    }

    fun clearContextualMode(){
        if (this::actionMode.isInitialized){
            actionMode.finish()
        }
    }

}