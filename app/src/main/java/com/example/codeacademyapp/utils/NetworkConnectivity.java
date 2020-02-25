package com.example.codeacademyapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetworkConnectivity {

    public static boolean isConnectivityNetworkAvailable (Context context){

        ConnectivityManager connectivityManager= (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;

        if (connectivityManager != null) {
            for(Network network : connectivityManager.getAllNetworks()){
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);

                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isConnected |= networkInfo.isConnected();
                }
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isConnected |= networkInfo.isConnected();
                }
            }
        }

        return !isConnected;
    }
}
