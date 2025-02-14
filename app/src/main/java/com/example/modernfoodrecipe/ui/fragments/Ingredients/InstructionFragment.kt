package com.example.modernfoodrecipe.ui.fragments.Ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.modernfoodrecipe.databinding.FragmentInstructionBinding
import com.example.modernfoodrecipe.viewmodels.DetailsViewmodel


class InstructionFragment : Fragment() {
    private var instfrag: FragmentInstructionBinding? = null
    private val  binding get() = instfrag!!

    private lateinit var viewmodel: DetailsViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        instfrag = FragmentInstructionBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(requireActivity()).get(DetailsViewmodel::class.java)


        val url = viewmodel.ingredients.value

        binding.instctionwebview.webViewClient = WebViewClient()
        binding.instctionwebview.loadUrl(url!!.sourceUrl)
        binding.instctionwebview.settings.setSupportZoom(true)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        instfrag=null
    }
}
