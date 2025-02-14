package com.example.modernfoodrecipe.ui.fragments.Recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.modernfoodrecipe.viewmodels.RecipeViewModel
import com.example.modernfoodrecipe.databinding.BottomSheetFragmentBinding
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipe.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class RecipiesBottomSheet:BottomSheetDialogFragment() {


    private var bottomsheet:BottomSheetFragmentBinding?=null
    private val binding get() = bottomsheet!!

    private var mealid=0
    private var mealtype = DEFAULT_MEAL_TYPE
    private var dietid=0
    private var diettype= DEFAULT_DIET_TYPE

    private lateinit var recipeViewModel: RecipeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bottomsheet = BottomSheetFragmentBinding.inflate(inflater,container,false)


        recipeViewModel.readmealanddiettypes.asLiveData().observe(viewLifecycleOwner, Observer { it->
            mealtype=it.selectedmealtype
            diettype=it.selecteddietype
           updataId(it.selectedmealid,binding.chipgroupmealtype)
            updataId(it.selecteddietid,binding.chipgroupdietype)
        })


        binding.chipgroupmealtype.setOnCheckedStateChangeListener { group, checkedIds ->
            val meal = group.findViewById<Chip>(group.checkedChipId)
            mealtype= meal.toString().lowercase(Locale.ROOT)
            mealid=group.checkedChipId
        }

        binding.chipgroupdietype.setOnCheckedStateChangeListener { group, checkedIds ->
           val diet = group.findViewById<Chip>(group.checkedChipId)
           diettype = diet.toString().lowercase(Locale.ROOT)
            dietid=group.checkedChipId
        }


        binding.applybtnbottomsheet.setOnClickListener {
            recipeViewModel.savemealdiettypes(mealid,mealtype,dietid,diettype)
            val action = RecipiesBottomSheetDirections.actionBottomSheetFragmentToRecipeFragment(true)
            findNavController().navigate(action)
        }





        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomsheet = null
    }

    private fun updataId(id:Int,chipGroup: ChipGroup){
        if (id!=0){
            chipGroup.findViewById<Chip>(id).isChecked=true
        }else{
        }
    }


}