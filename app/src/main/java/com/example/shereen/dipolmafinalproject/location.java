package com.example.shereen.dipolmafinalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class location extends FragmentActivity implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static String TAG="TEST4";
    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    double lat,lng;
    SharedPreferences sharedPreferences;
    String sharedPrefName = "Location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
        } else
        {
            showGPSDisabledAlertToUser();
        }


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d(TAG, "onCreate: ");
        }
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }


    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onConnected(null);
        } else {
            Toast.makeText(location.this, "No Permitions Granted", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                drawMarker(latLng);
            }
        });
    }
    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        lat=point.latitude;
        lng=point.longitude;

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(location.this,SignUp.class);
                String user_lat = String.valueOf(lat);
                String user_lng = String.valueOf(lng);
                addSharedPreference(user_lat , user_lng);
                Toast.makeText(this, "Your address is updated", Toast.LENGTH_SHORT).show();
                //location.this.finish();
                startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {

            Log.d(TAG, "onConnected:0 ");
            Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (userCurrentLocation != null) {
                Log.d(TAG, "onConnected:1 ");

                MarkerOptions currentUserLocation = new MarkerOptions();

                lat=userCurrentLocation.getLatitude();
                lng=userCurrentLocation.getLongitude();

                LatLng currentUserLatLang = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
                currentUserLocation.position(currentUserLatLang);
                 mMap.addMarker(currentUserLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUserLatLang, 16));

            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Go to Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    public void addSharedPreference(String lat, String lng)
    {
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString("lat" , lat);
        editor.putString("lng" , lng);

        editor.commit();


    }

}
