package com.example.covidtracker;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;

import com.example.covidtracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Intent intent = getIntent();
        String latitud = intent.getExtras().getString("lati");
        String longitud = intent.getExtras().getString("longi");
        String country = intent.getExtras().getString("country");
        Log.d("rohan","Done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Add a marker in Sydney and move the camera
        LatLng cLatLong = new LatLng(Integer.parseInt(latitud), Integer.parseInt(longitud));
        googleMap.addMarker(new MarkerOptions().position(cLatLong).title("Marker in " + country));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cLatLong));

        Toast.makeText(this, "Map of "+country, Toast.LENGTH_SHORT).show();

    }
}