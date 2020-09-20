package com.example.anotherweatherapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anotherweatherapp.AppDelegate;
import com.example.anotherweatherapp.R;
import com.example.anotherweatherapp.common.PresenterFragment;
import com.example.anotherweatherapp.common.RefreshOwner;
import com.example.anotherweatherapp.common.Refreshable;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;
import com.example.anotherweatherapp.utils.PermissionUtils;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import moxy.presenter.InjectPresenter;

public class MainFragment extends PresenterFragment<MainPresenter> implements MainView, Refreshable {

    public static final String TAG = MainFragment.class.getSimpleName();
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 999;

    private TextView tempTextView;
    private TextView iconPhraseTextView;
    private RefreshOwner mRefreshOwner;
    private RecyclerView mHourlyRecyclerView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    // private FusedLocationProviderClient fusedLocationClient;
    // private Location location;

    @Inject
    MainPresenter mPresenter;
    @Inject
    HourlyForecastAdapter mHourlyAdapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            mRefreshOwner = (RefreshOwner) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        if (PermissionUtils.isAccessLocationGranted(requireContext())) {
            if (PermissionUtils.isLocationEnabled(requireContext())) {
                getLocation();
            } else {
                PermissionUtils.showGPSNotEnabledDialog(requireContext());
            }
        } else {
            PermissionUtils.requestAccessLocationPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tempTextView = view.findViewById(R.id.deg_tv);
        iconPhraseTextView = view.findViewById(R.id.icon_phrase_tv);
        mHourlyRecyclerView = view.findViewById(R.id.hourly_recycler);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppDelegate.getAppComponent().inject(this);
        mPresenter.setView(this);

        mHourlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mHourlyRecyclerView.setAdapter(mHourlyAdapter);
        onRefreshData();
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError: " + error);
    }

    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showForecast(List<HourlyForecastsInfo> hourlyForecastsInfoList) {
        Log.d(TAG, "showForecast: " + hourlyForecastsInfoList.size());
        HourlyForecastsInfo hourlyForecastsInfo = hourlyForecastsInfoList.get(0);
        Log.d(TAG, "showForecast: " + hourlyForecastsInfo.getTemperature().getValue());
        tempTextView.setText(hourlyForecastsInfo.getTemperature().getValue().toString());
        iconPhraseTextView.setText(hourlyForecastsInfo.getIconPhrase());
        mHourlyAdapter.addData(hourlyForecastsInfoList);
    }


    @Override
    public void onRefreshData() {
        //mPresenter.getLocation();
        getLocation();
        mPresenter.getHourlyForecast();
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getLocation() {
        Log.d(TAG, "Start getLocation: ");
        // location = new Location("");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        SettingsClient settingsClient = LocationServices.getSettingsClient(requireContext());


       /* if(fusedLocationClient.getLastLocation()!=null){
          fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
              @Override
              public void onSuccess(Location location) {
                  location.getTime();
                  mPresenter.getLocation(location);
              }
          });
        }else {*/
         locationRequest = new LocationRequest().setInterval(2000).setFastestInterval(2000).setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

         locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d(TAG, "onLocationResult: 123");
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "onLocationResult: " + location.getLongitude() + location.getLatitude());
                }

                 /*   location.setLatitude(locationResult.getLastLocation().getLatitude());
                    location.setLongitude(locationResult.getLastLocation().getLongitude());*/
            }
        };
     //
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
       // LocationSettingsRequest locationSettingsRequest = builder.build();
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
       task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                   @Override
                   public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                       Log.d(TAG, "All location settings are satisfied.");
                       fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                   }
               })
       .addOnFailureListener(getActivity(), new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               if (e instanceof ResolvableApiException) {
                   // Location settings are not satisfied, but this can be fixed
                   // by showing the user a dialog.
                   try {
                       // Show the dialog by calling startResolutionForResult(),
                       // and check the result in onActivityResult().
                       ResolvableApiException resolvable = (ResolvableApiException) e;
                       resolvable.startResolutionForResult(getActivity(),
                               LOCATION_PERMISSION_REQUEST_CODE);
                   } catch (IntentSender.SendIntentException sendEx) {
                       // Ignore the error.
                   }
               }
           }
       });
        // mPresenter.getLocation(location);
        fusedLocationClient.removeLocationUpdates(locationCallback);
        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Logic to handle location object
                    Log.d(TAG, "getLocation last location : "+location.getLatitude()+" " + location.getLongitude());
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PermissionUtils.isLocationEnabled(getContext())) {
                        getLocation();
                    } else {
                        PermissionUtils.showGPSNotEnabledDialog(getContext());
                    }
                } else {
                    Toast.makeText(getContext(),
                            getString(R.string.location_permission_not_granted),
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

}