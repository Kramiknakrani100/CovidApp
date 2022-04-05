package com.example.covidtracker

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val intent = intent
        val latitud = intent.extras!!.getString("lati")
        val longitud = intent.extras!!.getString("longi")
        val country = intent.extras!!.getString("country")
        // Add a marker in Sydney and move the camera
        val cLatLong = LatLng(
            latitud!!.toFloat().toDouble(), longitud!!.toFloat().toDouble()
        )
        googleMap.addMarker(MarkerOptions().position(cLatLong).title("Marker in $country"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cLatLong))
        Toast.makeText(this, "Map of $country", Toast.LENGTH_SHORT).show()
    }
}