package com.example.modernfoodrecipe.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.modernfoodrecipe.models.ResultX
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewmodel @Inject constructor(private val application: Application) :AndroidViewModel(application) {

    var ingredients:LiveData<ResultX> = MutableLiveData()

}