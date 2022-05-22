package com.example.task91p;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.task91p.data.DatabaseHelper;
import com.example.task91p.model.Advert;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private static final String TAG = "Running";
    DatabaseHelper dbHelper;
    RadioButton lostRadioButton;
    RadioButton foundButton;
    EditText nameEditText;
    EditText phoneEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    Button saveButton;
    Button currentLocationButton;
    LocationManager locationManager;
    LocationListener locationListener;
    AutocompleteSupportFragment autocompleteFragment;
    String lastSelectedLocation;
    Boolean updateLocation = false;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, locationListener);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DatabaseHelper(this);

        lostRadioButton = findViewById(R.id.lostRadioButton);
        foundButton = findViewById(R.id.foundButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        saveButton = findViewById(R.id.saveButton);
        currentLocationButton = findViewById(R.id.currentLocationButton);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        lastSelectedLocation = null;
        Places.initialize(getApplicationContext(), getString(R.string.Place_Api));
        PlacesClient placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS));
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                lastSelectedLocation = place.getAddress();
                autocompleteFragment.setHint(lastSelectedLocation);
            }


            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateAdvertActivity.this,  "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ValidPostType())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please select a post type."
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!ValidName())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a name of at least 3 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidPhone())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a phone number of at least 8 digits.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDescription())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a description of at least 10 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDate())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a date in the format dd/MM/yyyy",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidLocation())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a location with at least 4 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SaveAdvert();
                    finish();
                }
            }
        });


        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation = true;
            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                if(updateLocation) {
                    MapLocation mapLocation = new MapLocation(location.getLongitude(), location.getLatitude());
                    lastSelectedLocation = GetNameFromLocation(mapLocation);
                    autocompleteFragment.setHint(lastSelectedLocation);
                    updateLocation = false;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateAdvertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void SaveAdvert()
    {
        Advert advert = new Advert(nameEditText.getText().toString(), phoneEditText.getText().toString(),
                descriptionEditText.getText().toString(), dateEditText.getText().toString(), lastSelectedLocation,
                GetPostType());

        dbHelper.InsertAdvert(advert);
    }

    private String GetPostType()
    {
        if(lostRadioButton.isChecked())
        {
            return lostRadioButton.getText().toString();
        }
        else
        {
            return foundButton.getText().toString();
        }
    }

    private boolean ValidPostType()
    {
        return lostRadioButton.isChecked() || foundButton.isChecked();
    }

    private boolean ValidName()
    {
        String name = nameEditText.getText().toString();
        return name != null && name.trim().length() > 2;
    }

    private boolean ValidPhone()
    {
        String phone = phoneEditText.getText().toString();
        return phone != null && phone.trim().length() > 7;
    }

    private boolean ValidDescription()
    {
        String description = descriptionEditText.getText().toString();
        return description != null && description.trim().length() > 9;
    }

    private boolean ValidDate()
    {
        String dateString = dateEditText.getText().toString();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean ValidLocation()
    {
        String location = lastSelectedLocation;
        return location != null && location.trim().length() > 3;
    }

    public String GetNameFromLocation(MapLocation mapLocation)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(mapLocation.latitude, mapLocation.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if(addresses == null || addresses.size() == 0)
        {
            return null;
        }

        Address location= addresses.get(0);

        return location.getAddressLine(0);

    }
}