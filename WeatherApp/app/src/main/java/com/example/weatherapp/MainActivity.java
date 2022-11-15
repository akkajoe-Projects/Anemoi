package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity<pubic> extends AppCompatActivity {

    public IMapController mapController;
    public FusedLocationProviderClient fusedLocationClient;
    String api_key = "6e6bd91309b9b77588424036888993a5";

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
        try {
            city_name(ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLastLocation(MapView map) {
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
//        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
//        Geocoder city_geocoder = new Geocoder(ctx);
        weatherhttp(city);
    }

    public void weatherhttp(String city) throws MalformedURLException {
        String base_url = "https://api.openweathermap.org/data/2.5/weather?q=";
        String url = base_url + "Jaipur" + "&appid="+ api_key;
        Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, stringRequest, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
//        requestqueue.add(stringRequest);

        JsonObjectRequest jsonObjectRequest	= new JsonObjectRequest(url,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {Toast.makeText(getApplicationContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(jsonObjectRequest);








//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept", "application/json");
//            String a = String.valueOf(connection.getResponseCode());
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.connect();
//            String response = connection.getResponseCode() + " " + connection.getResponseMessage();
//            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//            System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());
//            connection.disconnect();
//            // Reading the response
//            StringBuffer buffer = new StringBuffer();
//            InputStream is = connection.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String line= null;
//            while ((line=br.readLine())!=null);
//            buffer.append(line+"rn");
//            is.close();
//            connection.disconnect();
//            String response = buffer.toString()+"RESPONSE";
//            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//            return buffer.toString();
//        }
//
//
//        return base_url;
//    }

    }
}

