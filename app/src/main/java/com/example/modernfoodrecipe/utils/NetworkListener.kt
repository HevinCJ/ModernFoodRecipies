package com.example.modernfoodrecipe.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener:ConnectivityManager.NetworkCallback() {

    private var isNetworkAvailable = MutableStateFlow(false)
    var isconnected = false

     fun checkConnectionAvailability(context: Context):MutableStateFlow<Boolean>{

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         connectivityManager.registerDefaultNetworkCallback(this)

         connectivityManager.allNetworks.forEach { network ->
             val capability = connectivityManager.getNetworkCapabilities(network)
             capability?.let {
                 if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                      isconnected = true
                     return@forEach
                 }
             }
         }

       isNetworkAvailable.value=isconnected
         return  isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        isNetworkAvailable.value=true
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        isNetworkAvailable.value=false
    }
}