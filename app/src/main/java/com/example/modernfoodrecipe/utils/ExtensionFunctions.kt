package com.example.modernfoodrecipe.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer



    fun<T>LiveData<T>.ObserveOnce(lifecycleOwner: LifecycleOwner,observer: Observer<T>){
        observe(lifecycleOwner,object :Observer<T>{
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this)
            }

        })
    }

