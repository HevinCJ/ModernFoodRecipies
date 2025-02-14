package com.example.modernfoodrecipe.ui.fragments.FoodJoke

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.viewmodels.MainViewModel
import com.example.modernfoodrecipe.utils.NetworkResult
import com.example.modernfoodrecipe.databinding.FragmentFoodJokeBinding
import com.example.modernfoodrecipe.utils.Constants.Companion.API_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Food_joke : Fragment() {
    private var foodjokefrag:FragmentFoodJokeBinding?=null
    private val binding get() = foodjokefrag!!

    private val mainViewModel by viewModels<MainViewModel>()
    private var foodjoke = "No Food Joke"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       foodjokefrag = FragmentFoodJokeBinding.inflate(inflater,container,false)
        binding.mainviewmodel = mainViewModel
        binding.lifecycleOwner=viewLifecycleOwner

        mainViewModel.getFoodJoke(API_KEY)
        requestFoodJokeFromApi()

        binding.foodJokeTextview.movementMethod = ScrollingMovementMethod()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuhost: MenuHost = requireActivity()

        menuhost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.foodjoke_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              when(menuItem.itemId){
                  R.id.sharefoodjoke->{
                      val shareintent = Intent().apply {
                          this.action = Intent.ACTION_SEND
                          this.putExtra(Intent.EXTRA_TEXT, "$foodjoke Shared from ModernFoodRecipe")
                          this.type="text/plain"
                      }
                      val sendintent = Intent.createChooser(shareintent,null)
                      startActivity(sendintent)

                  }

              }
                return true

            }


        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadDataFromCache() {
        mainViewModel.getfoodjoke.observe(viewLifecycleOwner){database->
            if(!database.isNullOrEmpty()){
                binding.foodJokeTextview.text = database[0].foodJoke.text
                foodjoke = database[0].foodJoke.text.toString()
            }
        }
    }

    private fun requestFoodJokeFromApi(){
        mainViewModel.foodJokeresponse.observe(viewLifecycleOwner){ response->
            when(response){

                is NetworkResult.Success ->{
                    binding.foodJokeTextview.text = response.data?.text
                    foodjoke= response.data?.text.toString()
                }

                is NetworkResult.Error ->{
                    Toast.makeText(requireContext(),response.message.toString(),Toast.LENGTH_SHORT).show()
                    loadDataFromCache()
                }

                is NetworkResult.Loading ->{
                }

                else -> {}
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        foodjokefrag=null

    }
}