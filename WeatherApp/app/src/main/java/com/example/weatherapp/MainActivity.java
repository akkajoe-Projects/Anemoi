package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public IMapController mapController;
    public FusedLocationProviderClient fusedLocationClient;
    String api_key = "6e6bd91309b9b77588424036888993a5";
    public Button btn;

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
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        try {
            getLastLocation(map, ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLastLocation(MapView map, Context ctx) throws IOException {
        String CHECK = "CHECK 1";
        String CHECK2 = "CHECK 2";
        String CHECK3 = "CHECK 3";
//        Toast.makeText(MainActivity.this, CHECK, Toast.LENGTH_SHORT).show();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        Toast.makeText(MainActivity.this, CHECK2, Toast.LENGTH_SHORT).show();
        int locationRequestCode = 1000;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
            String per = "permission not granted";
            Toast.makeText(MainActivity.this, per, Toast.LENGTH_SHORT).show();
        } else {
            String per2 = "Permission Granted";
            Toast.makeText(MainActivity.this, per2, Toast.LENGTH_SHORT).show();
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, (location) -> {

            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                String Latitude = String.valueOf(lat);
                String Longitude = String.valueOf(lon);
//                Toast.makeText(MainActivity.this, Latitude, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, Longitude, Toast.LENGTH_SHORT).show();
                GeoPoint startPoint = new GeoPoint(lat, lon);
                mapController.setCenter(startPoint);
                Marker live_marker = new Marker(map);
                live_marker.setPosition(new GeoPoint(lat, lon));
//                live_marker.setIcon(new ColorDrawable(getResources().getColor(android.R.color.holo_red_dark)));
                live_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(live_marker);
            }
        });

    }

    public void city_name(Context ctx) throws IOException {
        EditText city_input = (EditText) findViewById(R.id.city_input);
        String city = city_input.getText().toString();
        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
        if (city.equals(" ")) {
            Toast.makeText(MainActivity.this, "EMPTY CITY", Toast.LENGTH_SHORT).show();
        }
        weatherhttp(city);
//        geocoding(city);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            try {
                city_name(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void weatherhttp(String city) throws MalformedURLException {
        String base_url = "https://api.openweathermap.org/data/2.5/weather?q=";
        String url = base_url + city + "&appid="+ api_key;
        Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest	= new JsonObjectRequest(url,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject coordobj = response.getJSONObject("coord");
                            String lat = coordobj.getString("lat");
                            String lon = coordobj.getString("lon");
                            double double_lat = Double.parseDouble(lat);
                            double double_long = Double.parseDouble(lon);
                            Toast.makeText(MainActivity.this, "Lat: " + double_lat + "long: "+ double_long, Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = response.getJSONArray("Weather");
                            JSONObject weatherobj = jsonArray.getJSONObject(0);
                            String description = weatherobj.getString("description");
                            JSONObject mainobj = response.getJSONObject("main");
                            double temp = mainobj.getDouble("temp")-273.15;
                            int humidity = mainobj.getInt("humidity");
                            JSONObject windobj = response.getJSONObject("wind");
                            JSONObject cloudobj = response.getJSONObject("clouds");
                            String clouds = cloudobj.getString("all");
                            String wind = windobj.getString("speed");
                            JSONObject sysobj = response.getJSONObject("sys");
                            String Country = response.getString("country");
                            GeoPoint citypoint = new GeoPoint(double_lat, double_long);
                            MapView map = (MapView) findViewById(R.id.mapView);
//                            map.getController().animateTo(citypoint);
                            mapController = map.getController();
                            mapController.setCenter(citypoint);
                            mapController.setZoom(10);
                            Marker city_marker = new Marker(map);
                            city_marker.setPosition(new GeoPoint(double_lat, double_long));
                            city_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            map.getOverlays().add(city_marker);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {Toast.makeText(getApplicationContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        requestqueue.add(jsonObjectRequest);
    }}


//    public void geocoding(String city) {
//    String geo_url = "http://api.openweathermap.org/geo/1.0/direct?q="+city+"&appid="+api_key;
//        JsonObjectRequest jsonObjectRequest	= new JsonObjectRequest(geo_url,new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject geoloc)
//            {Toast.makeText(MainActivity.this, "GEO RESPONSE: " + geoloc, Toast.LENGTH_LONG).show();
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
////                        Toast.makeText(getApplicationContext(), "U: "+error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
//        requestqueue.add(jsonObjectRequest);
//
//
//
//    }}


