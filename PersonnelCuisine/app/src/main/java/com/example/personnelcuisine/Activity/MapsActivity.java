package com.example.personnelcuisine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.personnelcuisine.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.personnelcuisine.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(31.525921240334135, 74.34221091831338);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Lahore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get the ID of the selected menu item
        int itemId = item.getItemId();

        // Change the map type based on the user's selection.
        if (itemId == R.id.normal_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        } else if (itemId == R.id.hybrid_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        } else if (itemId == R.id.satellite_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        } else if (itemId == R.id.terrain_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }




}