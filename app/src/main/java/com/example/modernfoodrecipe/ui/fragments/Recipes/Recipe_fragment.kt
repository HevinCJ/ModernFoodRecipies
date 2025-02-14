package com.example.modernfoodrecipe.ui.fragments.Recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import com.example.modernfoodrecipe.Adapter.RecipesAdapter
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.viewmodels.MainViewModel
import com.example.modernfoodrecipe.utils.NetworkResult
import com.example.modernfoodrecipe.viewmodels.RecipeViewModel
import com.example.modernfoodrecipe.databinding.FragmentRecipeFragmentBinding
import com.example.modernfoodrecipe.utils.ObserveOnce
import com.example.modernfoodrecipe.utils.NetworkListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Recipe_fragment : Fragment(), SearchView.OnQueryTextListener {

    private var Recipe_fragment:FragmentRecipeFragmentBinding ?=null
    private val binding get() = Recipe_fragment!!

    private val adapter by lazy { RecipesAdapter() }

    private val args by navArgs<Recipe_fragmentArgs>()


    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipeViewModel

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       Recipe_fragment = FragmentRecipeFragmentBinding.inflate(inflater,container,false)

        Coil.imageLoader(requireContext())


        binding.mainviewmodel=mainViewModel
        binding.lifecycleOwner=this

        setrecyclerview()
        loadDataFromCache()

        lifecycleScope.launch {
            networkListener=NetworkListener()
            networkListener.checkConnectionAvailability(requireContext())
                networkListener.checkConnectionAvailability(requireContext()).collect{status->
                    Log.d("NetworkListener", status.toString())
                    recipeViewModel.networkstatus=status
                    recipeViewModel.shownetworkstatus()
                    readFromDatabase()
                }
        }


        binding.fabtnselecttype.setOnClickListener {
            if (recipeViewModel.networkstatus) {
                findNavController().navigate(R.id.action_recipe_fragment_to_bottom_sheet_fragment)
            }
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuhost: MenuHost = requireActivity()

        menuhost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.recipes_menu, menu)
                val search = menu.findItem(R.id.app_bar_search)
                val searchView = search.actionView as SearchView
                searchView.isSubmitButtonEnabled=true
                searchView.setOnQueryTextListener(this@Recipe_fragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }


        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadDataFromCache() {
       lifecycleScope.launch {
           Log.d("loadfromcache","loaded from cache")
           mainViewModel.getalldata.observe(viewLifecycleOwner, Observer { database ->
               if (database.isNotEmpty()) {
                   adapter.setdata(database[0].foodRecipes)
               }
           })
       }
    }

    private fun readFromDatabase() {
        lifecycleScope.launch {
            mainViewModel.getalldata.ObserveOnce(viewLifecycleOwner, Observer { database ->
                if (database.isNotEmpty() && !args.backtobottomsheet) {
                    adapter.setdata(database[0].foodRecipes)
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun showshimmereffect(){
     binding.shimmerFrameLayout.isVisible=true
        binding.rcviewrecipe.isVisible=false
    }
    private fun hideshimmer(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.isVisible=false
        binding.rcviewrecipe.isVisible=true
    }

    private fun setrecyclerview(){
        binding.rcviewrecipe.adapter=adapter
        binding.rcviewrecipe.layoutManager = LinearLayoutManager(requireContext())
        hideshimmer()
    }


    private fun requestApiData(){
        Log.d("request api","Its called")
        mainViewModel.getrecipes(recipeViewModel.queries())
        mainViewModel.reciperesponse.observe(viewLifecycleOwner) { response->
            showshimmereffect()
            when(response){
               is NetworkResult.Error -> {
                   hideshimmer()
                   loadDataFromCache()
                   Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
               }

                is NetworkResult.Loading -> showshimmereffect()

                is NetworkResult.Success-> response.data?.let {
                    adapter.setdata(it)
                    hideshimmer()
                }
               else ->{}
            }
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
      if (p0!=null) {
          searchrecipies(p0)
      }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0!=null) {
            searchrecipies(p0)
        }
        return true
    }

    private fun searchrecipies(searchquery: String) {
        showshimmereffect()
        mainViewModel.searchrecipies(recipeViewModel.stringToQuery(searchquery))
        mainViewModel.searchreciperesponse.observe(viewLifecycleOwner) { response->
            when(response){
                is NetworkResult.Error -> {
                    hideshimmer()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> showshimmereffect()

                is NetworkResult.Success-> response.data?.let {
                    adapter.setdata(it)
                    hideshimmer()
                }
                else ->{
                    showshimmereffect()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        Recipe_fragment=null
    }
}