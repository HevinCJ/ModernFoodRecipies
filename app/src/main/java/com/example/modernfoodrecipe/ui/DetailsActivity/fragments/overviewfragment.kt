package com.example.modernfoodrecipe.ui.DetailsActivity.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.modernfoodrecipe.R
import com.example.modernfoodrecipe.databinding.FragmentOverviewfragmentBinding
import com.example.modernfoodrecipe.ui.DetailsActivity.DetailsViewmodel
import org.jsoup.Jsoup


class overviewfragment : Fragment() {

    private var fragmentoverview:FragmentOverviewfragmentBinding?=null
    private val binding get() = fragmentoverview!!

    private lateinit var viewModel: DetailsViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       viewModel = ViewModelProvider(requireActivity()).get(DetailsViewmodel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentoverview = FragmentOverviewfragmentBinding.inflate(inflater,container,false)


        val mybundle = viewModel.ingredients.value

        Log.d("mybundle",mybundle.toString())

        binding.imgviewrecipe.load(mybundle?.image)
        binding.txtviewtime.text = mybundle?.readyInMinutes.toString()
        binding.txtviewlikes.text=mybundle?.aggregateLikes.toString()
        binding.txtviewtitle.text=mybundle?.title
        binding.txtviewsummary.text= mybundle?.summary?.let { Jsoup.parse(it).text() }


        when (mybundle?.cheap) {
            true -> {
                binding.Cheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.Cheapimage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.Cheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }

        when (mybundle?.dairyFree) {
            true -> {
                binding.diearyfree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.diearyfreeimage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.diearyfree.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }

        when (mybundle?.veryHealthy) {
            true -> {
                binding.healthvtxtview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.healthimgview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.healthvtxtview.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }

        when (mybundle?.vegan) {
            true -> {
                binding.vegantextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.Veganimageview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.vegantextview.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }


        when (mybundle?.glutenFree) {
            true -> {
                binding.gltnfreetxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.gltnfreeimage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.gltnfreetxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }

        when (mybundle?.vegan) {
            true -> {
                binding.vegtxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.vegimg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            }
            else -> {
                binding.vegtxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.mediumGray))
            }
        }





        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        fragmentoverview=null
    }


}