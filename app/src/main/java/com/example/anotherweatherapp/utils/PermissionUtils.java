package com.example.anotherweatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.anotherweatherapp.R;


public class PermissionUtils {

   /*public static void requestAccessLocationPermission(Context context, int requestId) {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                requestId);
    }*/

    public static Boolean isAccessLocationGranted(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public static Boolean isLocationEnabled(Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static void showGPSNotEnabledDialog(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.enable_gps))
                .setMessage(context.getString(R.string.gps_dialog_message))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.turn_on_gps),
                        (dialogInterface, i) ->
                                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .show();
    }

}
