package com.example.task91p;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.task91p.data.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.task91p.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.task91p.model.Advert;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ArrayList advertArrayList;
    DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);

        advertArrayList = dbHelper.GetAdverts();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        for (Object advert : advertArrayList) {
            Advert castedAdvert = (Advert) advert;

            MapLocation mapLocation = GetLocationFromName(castedAdvert.getAdvertLocation());
            LatLng advertLocation = new LatLng(mapLocation.latitude, mapLocation.longitude);
            mMap.addMarker(new MarkerOptions().position(advertLocation).title(castedAdvert.getAdvertName() + " " + castedAdvert.getAdvertDescription() + " " + castedAdvert.getAdvertLocation()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(advertLocation));
        }

    }

    public MapLocation GetLocationFromName(String streetAddress)
    {
        Geocoder coder = new Geocoder(this);
        List<Address> addresses = null;

        try {
            addresses = coder.getFromLocationName(streetAddress,1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if(addresses == null || addresses.size() == 0)
        {
            return null;
        }

        Address location= addresses.get(0);
        return new MapLocation(location.getLongitude(), location.getLatitude());
    }



}