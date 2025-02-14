package com.example.modernfoodrecipe.ui.fragments.Ingredients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernfoodrecipe.databinding.FragmentIngredientfragmentBinding
import com.example.modernfoodrecipe.viewmodels.DetailsViewmodel
import kotlinx.coroutines.launch


class Ingredientfragment : Fragment() {
    private var ingfrag:FragmentIngredientfragmentBinding?=null
    private val binding get() = ingfrag!!

    private val adapter by lazy { IngfragAdapter() }
    private lateinit var viemodel: DetailsViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viemodel = ViewModelProvider(requireActivity()).get(DetailsViewmodel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ingfrag = FragmentIngredientfragmentBinding.inflate(inflater,container,false)

        viewLifecycleOwner.lifecycleScope.launch {
            viemodel.ingredients.observe(viewLifecycleOwner) {result->
                adapter.setingredientdata(result.extendedIngredients)
                Log.d("loadedresult",result.toString())
            }
        }




        binding.recyclerviewingredientfrag.adapter = adapter
        binding.recyclerviewingredientfrag.layoutManager = LinearLayoutManager(requireContext())





        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        ingfrag=null
    }


}