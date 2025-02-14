package com.example.modernfoodrecipe.ui.fragments.Favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.viewmodels.MainViewModel
import com.example.modernfoodrecipe.databinding.FragmentFavouriteFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favourite_fragment : Fragment() {
    private var favfrag:FragmentFavouriteFragmentBinding?=null
    private val binding get() = favfrag!!

    private val viewmodel: MainViewModel by viewModels()
    private val adapter: FavfragAdapter by lazy { FavfragAdapter(requireActivity(),viewmodel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favfrag = FragmentFavouriteFragmentBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner = this
        binding.mainviewmodel = viewmodel


        viewmodel.readfavouriterecipes.observe(viewLifecycleOwner){
            adapter.setdata(it)
        }



        binding.favrcView.adapter= adapter
        binding.favrcView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menu:MenuHost = requireActivity()

        menu.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favourite_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              when(menuItem.itemId){
                  R.id.delete_all ->{
                          viewmodel.deleteall()
                          showSnackBar("All Favourites are Deleted")
                  }
              }
               return true
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }


    private fun showSnackBar(message:String){
        Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        favfrag = null
        adapter.clearContextualMode()

    }

}