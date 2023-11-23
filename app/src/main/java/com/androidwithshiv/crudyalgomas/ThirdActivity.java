package com.androidwithshiv.crudyalgomas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.androidwithshiv.crudoperation.R;
import com.google.android.gms.maps.model.MarkerOptions;

public class ThirdActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configura la posición y zoom de la cámara
        LatLng chillan = new LatLng(-36.6064, -72.1034);  // Coordenadas de Chillán, Chile
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chillan));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chillan, 15));  // Puedes ajustar el nivel de zoom según tus preferencias

        // Agrega un marcador en Chillán
        mMap.addMarker(new MarkerOptions().position(chillan).title("Chillán, Chile"));
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}