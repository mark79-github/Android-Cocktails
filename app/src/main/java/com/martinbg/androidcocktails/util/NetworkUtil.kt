package com.martinbg.androidcocktails.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*

object NetworkUtil {

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return if (capabilities.hasTransport(TRANSPORT_CELLULAR)) {
                true
            } else if (capabilities.hasTransport(TRANSPORT_WIFI)) {
                true
            } else capabilities.hasTransport(TRANSPORT_ETHERNET)
        }

        return false
    }
}