package com.example.weatherapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ButtonClick {
    public IMapController mapController;
    public FusedLocationProviderClient fusedLocationClient;
    String api_key = "6e6bd91309b9b77588424036888993a5";
    public Button btn;
    public MapView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        String map1 = String.valueOf(map);
        Toast.makeText(MainActivity.this, "BEFORE CHECK"+map1, Toast.LENGTH_SHORT).show();
        map = (MapView) findViewById(R.id.mapView);
        String map2 = String.valueOf(map);
        Log.d("MAP A", map2);

        Toast.makeText(MainActivity.this, "AFTER CHECK"+map2, Toast.LENGTH_SHORT).show();

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
        city_input.setText("");
        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
        if (city.equals(" ")) {
            Toast.makeText(MainActivity.this, "EMPTY CITY", Toast.LENGTH_SHORT).show();
        }
        weatherhttp(city);

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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
                        double double_long = 0;
                        double double_lat = 0;
                        double temp = 0;
                        String description = "";
                        int humidity = 0;
                        String icon_code="";
                        String wind_speed = "";
                        try {
                            JSONObject coordobj = response.getJSONObject("coord");
                            String lat = coordobj.getString("lat");
                            String lon = coordobj.getString("lon");
                            double_lat = Double.parseDouble(lat);
                            double_long = Double.parseDouble(lon);
                            String map3 = String.valueOf(map);
//                            Toast.makeText(MainActivity.this, "RESULT" + map3, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = response.getJSONArray("weather");
                            JSONObject weatherobj = jsonArray.getJSONObject(0);
                            description = weatherobj.getString("description");
                            icon_code = weatherobj.getString("icon");
                            JSONObject mainobj = response.getJSONObject("main");
                            temp = Math.round(mainobj.getDouble("temp")-273.15);
                            humidity = mainobj.getInt("humidity");
                            JSONObject windobj = response.getJSONObject("wind");
                            JSONObject cloudobj = response.getJSONObject("clouds");
                            String clouds = cloudobj.getString("all");
                            wind_speed = windobj.getString("speed");
                            JSONObject sysobj = response.getJSONObject("sys");
                            String Country = response.getString("country");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        map_recenter(double_lat, double_long);
                        TextView city_text = (TextView) findViewById(R.id.City);
                        city_text.setText(city);
                        TextView temp_text = (TextView) findViewById(R.id.Temp);
                        temp_text.setText(String.valueOf(temp+"\u00B0")+"c");
                        TextView description_text = (TextView) findViewById(R.id.Description);
                        description_text.setText(description);
                        TextView humidity_text = (TextView) findViewById(R.id.Humidity);
                        humidity_text.setText(humidity+"%");
                        get_icon(icon_code);
                        TextView wind_speed_text = (TextView) findViewById(R.id.Wind);
                        wind_speed_text.setText(wind_speed+"m/s");
                        Toast.makeText(MainActivity.this,wind_speed+"WINDDD", Toast.LENGTH_SHORT).show();

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
    }

public void map_recenter(double lat, double lon) {
    map.getController().animateTo(new GeoPoint(lat, lon));
}
public void get_icon(String icon_code) {
    Toast.makeText(getApplicationContext(),icon_code, Toast.LENGTH_SHORT).show();
    RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
    String icon_owp_url = "https://openweathermap.org/img/wn/"+icon_code+"@2x.png";
    int max_width=300;
    int max_height=300;
    ImageRequest imageRequest = new ImageRequest(icon_owp_url, new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap response) {
            ImageView imageView = (ImageView) findViewById(R.id.icon);
            imageView.setImageBitmap(response);
        }
    },max_width, max_height, null,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {Log.d("image_Error", String.valueOf(error));
                    Toast.makeText(getApplicationContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
    requestqueue.add(imageRequest);

}
}



