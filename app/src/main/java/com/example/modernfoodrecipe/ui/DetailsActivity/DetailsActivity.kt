package com.example.modernfoodrecipe.ui.DetailsActivity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.data.FavouriteEntity
import com.example.modernfoodrecipe.data.MainViewModel
import com.example.modernfoodrecipe.databinding.ActivityDetailsBinding
import com.example.modernfoodrecipe.ui.DetailsActivity.fragments.Ingredientfragment
import com.example.modernfoodrecipe.ui.DetailsActivity.fragments.InstructionFragment
import com.example.modernfoodrecipe.ui.DetailsActivity.fragments.overviewfragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var savedrecipeid = 0
    private var addedtofavourite = false

    private val savedRecipeIds = mutableListOf<Int>()



    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()

    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel = ViewModelProvider(this).get(DetailsViewmodel::class.java)
        binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        setSupportActionBar(binding.toolbardetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launch {
            viewmodel.ingredients = MutableLiveData(args.Result)
            Log.d("argsresult1",args.Result.toString())
        }


        val fragments = ArrayList<Fragment>()
        fragments.add((overviewfragment()))
        fragments.add(Ingredientfragment())
        fragments.add(InstructionFragment())


        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val adapter = PagerAdapter(fragments,this)

        binding.viewpagerdetails.adapter=adapter

        val tabLayout = binding.tblayout
        val viewPager = binding.viewpagerdetails

        TabLayoutMediator(tabLayout,viewPager){tab,postion->
            tab.text=titles[postion]
        }.attach()


        binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)

        val item = menu.findItem(R.id.addtofavourite)
        checkSavedRecipes(item)
        return true

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.addtofavourite -> {
                when (addedtofavourite) {
                    true -> removefromfavourites(item)
                    false -> addtoFavourites(item)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removefromfavourites(item: MenuItem) {
        try {
            val favouriteEntity = FavouriteEntity(savedrecipeid,args.Result!!)
            mainViewModel.deletefavouriteRecipes(favouriteEntity
            )
            showSnackBar("Removed from Favourites")
            changeMenuItemColor(item, R.color.white)
            addedtofavourite = false
        }catch (e:Exception){
            showSnackBar("Something error Occured")
        }
    }


    private fun addtoFavourites(item: MenuItem) {
       try {
           val favouriteEntity = FavouriteEntity(0,args.Result!!)
           mainViewModel.insertfavouriterecipes(favouriteEntity)
           changeMenuItemColor(item, R.color.yellow)
           showSnackBar("Added to Favourites")
            addedtofavourite = true
       }catch (e:Exception){
           showSnackBar("Something error Occured")
       }
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
            mainViewModel.readfavouriterecipes.observe(this@DetailsActivity) { favoritesEntity ->
                try {
                    for (savedRecipe in favoritesEntity) {
                        when {
                            savedRecipe.result.id == args.Result?.id -> {
                                savedrecipeid = savedRecipe.id
                                changeMenuItemColor(menuItem, R.color.yellow)
                                addedtofavourite = true
                            }
                            savedRecipe.result.id != args.Result?.id -> {
                                changeMenuItemColor(menuItem, R.color.white)
                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.d("DetailsActivity", e.message.toString())
                }
            }

    }


    private fun showSnackBar(message: String) {
        Snackbar.make(
           binding.tblayout,message,Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this,color))
    }


}