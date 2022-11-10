package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    public IMapController mapController;
    public FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        MapView map = (MapView) findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(10);
        getLastLocation(map);
    }

    public void getLastLocation(MapView map) {
        String CHECK = "CHECK 1";
        String CHECK2 = "CHECK 2";
        String CHECK3 = "CHECK 3";
        Toast.makeText(MainActivity.this, CHECK, Toast.LENGTH_SHORT).show();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Toast.makeText(MainActivity.this, CHECK2, Toast.LENGTH_SHORT).show();
        int locationRequestCode = 1000;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            locationRequestCode);
            String per= "permission not granted";
            Toast.makeText(MainActivity.this, per, Toast.LENGTH_SHORT).show();
        }
        else {  String per2 = "Permission Granted";
                Toast.makeText(MainActivity.this, per2, Toast.LENGTH_SHORT).show(); }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, (location) -> {

            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                String Latitude = String.valueOf(lat);
                String Longitude = String.valueOf(lon);
                Toast.makeText(MainActivity.this, Latitude, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, Longitude, Toast.LENGTH_SHORT).show();
                GeoPoint startPoint = new GeoPoint(lat, lon);
                mapController.setCenter(startPoint);
                Marker live_marker = new Marker(map);
                live_marker.setPosition(new GeoPoint(lat, lon));
//                live_marker.setIcon(new ColorDrawable(getResources().getColor(android.R.color.holo_red_dark)));
                live_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(live_marker);
//                live_marker.showInfoWindow();

            }
        });
        }





    public void city_name(View view) {
            EditText city_input = (EditText)findViewById(R.id.city_input);
            String city = city_input.getText().toString();
            Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();

        }
}