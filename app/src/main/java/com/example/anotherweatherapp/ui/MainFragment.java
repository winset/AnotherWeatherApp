package com.example.anotherweatherapp.ui;

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
import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.data.model.Hourly;
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

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainFragment extends PresenterFragment<MainPresenter> implements MainView, Refreshable {

    public static final String TAG = MainFragment.class.getSimpleName();
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 999;

    private TextView tempTextView;
    private TextView iconPhraseTextView;
    private RefreshOwner mRefreshOwner;
    private RecyclerView mHourlyRecyclerView;
    private RecyclerView mDailyRecyclerView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Inject
    MainPresenter mPresenter;
    @Inject
    HourlyForecastAdapter mHourlyAdapter;
    @Inject
    DailyForecastAdapter mDailyAdapter;


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
            if (!PermissionUtils.isLocationEnabled(requireContext())) {
                //getLocation();
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
        mDailyRecyclerView = view.findViewById(R.id.daily_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppDelegate.getAppComponent().inject(this);
        mPresenter.setView(this);

        mHourlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mDailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDailyRecyclerView.setHasFixedSize(true);
        mHourlyRecyclerView.setHasFixedSize(true);
        mHourlyRecyclerView.setAdapter(mHourlyAdapter);
        mDailyRecyclerView.setAdapter(mDailyAdapter);
        onRefreshData();
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError: " + error);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showCurrentForecast(Example example) {
        Log.d(TAG, "showCurrentForecast: "+ example.getTimezone());
        tempTextView.setText(example.getCurrent().getTemp().toString());
        iconPhraseTextView.setText(example.getCurrent().getWeather().get(0).getMain());
    }

    @Override
    public void showHourlyForecast(List<Hourly> hourlyList) {
        mHourlyAdapter.addData(hourlyList);
    }

    @Override
    public void showDailyForecast(List<Daily> dailyList) {
        mDailyAdapter.addData(dailyList);
    }

    @Override
    public void onRefreshData() {
        //mPresenter.getLocation();
        getLocation();
        // mPresenter.getForecast();
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        SettingsClient settingsClient = LocationServices.getSettingsClient(requireContext());

        locationRequest = new LocationRequest().setInterval(2000).setFastestInterval(2000).setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "onLocationResult: " + location.getLongitude() + location.getLatitude());
                    mPresenter.setLocation(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
                    mPresenter.getForecast();
                }

            }
        };

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, "All location settings are satisfied.");
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
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

        fusedLocationClient.removeLocationUpdates(locationCallback);
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
