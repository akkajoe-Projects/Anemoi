package com.example.weatherapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ButtonClick, MapEventsReceiver {
    public IMapController mapController;
    public FusedLocationProviderClient fusedLocationClient;
    String api_key = "6e6bd91309b9b77588424036888993a5";
    public Button btn;
    public MapView map;
    List<Marker> marker_list = new ArrayList<Marker>();
    String client_id= "bda9d827920c473c995b4beca05c0a58";
    String redirect_uri= "https://localhost:8888/callback";
    private static final int request_code = 1337;
    private static final String scopes = "user-read-recently-played user-library-modify user-read-email user-read-private";
    private static Intent intent;
    public String weather_description="";
    private GifImageView music_icon;
    public ArrayList<String> artist_list = new ArrayList<String>();
    public ArrayList<String> song_list = new ArrayList<String>();
    public String main_string="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        String map1 = String.valueOf(map);
        map = (MapView) findViewById(R.id.mapView);
        String map2 = String.valueOf(map);
        Log.d("MAP A", map2);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(10);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        //Music suggestion stuff
        music_icon = findViewById(R.id.MusicIcongif);
        music_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_sugg();
            }
        });


        try {
            getLastLocation(map, ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapEventsOverlay overlay = new MapEventsOverlay(this, this);
        map.getOverlays().add(overlay);
    }

    public void getLastLocation(MapView map, Context ctx) throws IOException {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
                live_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(live_marker);
                live_marker.setInfoWindow(null);
                mapController.setZoom(15);

                String closest_city_url = "https://api.openweathermap.org/data/2.5/weather?lat=" + Latitude + "&lon=" + Longitude + "&appid=" + api_key;
                JsonObjectRequest live_json_data = new JsonObjectRequest(closest_city_url, new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(MainActivity.this, "Response: " + String.valueOf(response), Toast.LENGTH_LONG).show();
                        String city = null;
                        double temp = 0;
                        String weather_description = null;
                        int humidity = 0;
                        String icon_code = null;
                        String wind_speed = null;
                        String cloudiness = null;
                        try {
                            JSONArray weather_array = response.getJSONArray("weather");
                            JSONObject weatherobj = weather_array.getJSONObject(0);
                            weather_description = weatherobj.getString("description");
                            icon_code = weatherobj.getString("icon");
                            JSONObject mainobj = response.getJSONObject("main");
                            main_string = weatherobj.getString("main");
                            temp = Math.round(mainobj.getDouble("temp") - 273.15);
                            humidity = mainobj.getInt("humidity");
                            JSONObject windobj = response.getJSONObject("wind");
                            JSONObject cloudobj = response.getJSONObject("clouds");
                            cloudiness = cloudobj.getString("all");
                            wind_speed = windobj.getString("speed");
                            city = response.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this,"MAIN "+ main_string,Toast.LENGTH_SHORT).show();
                        TextView city_text = (TextView) findViewById(R.id.City);
                        city_text.setText(city);
                        TextView temp_text = (TextView) findViewById(R.id.Temp);
                        temp_text.setText("Temperature: " + String.valueOf(temp + "\u00B0") + "c");
                        TextView description_text = (TextView) findViewById(R.id.Description);
                        description_text.setText(weather_description);
                        TextView humidity_text = (TextView) findViewById(R.id.Humidity);
                        humidity_text.setText("Humidity: " + humidity + "%");
                        get_icon(icon_code);
                        TextView wind_speed_text = (TextView) findViewById(R.id.Wind);
                        wind_speed_text.setText("Wind Speed: " + wind_speed + "m/s");
//                        Toast.makeText(MainActivity.this, wind_speed + "WINDDD", Toast.LENGTH_SHORT).show();
                        TextView cloud_text = (TextView) findViewById(R.id.Clouds);
                        cloud_text.setText("Cloudiness: " + cloudiness + "%");

                    }
                }, new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse (VolleyError error){
//                        Toast.makeText(getApplicationContext(), "Error Click: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
                requestqueue.add(live_json_data);

            }
        });
        spotify_authorization();
    }

    public void city_name(Context ctx) throws IOException {
        EditText city_input = (EditText) findViewById(R.id.city_input);
        String city = city_input.getText().toString();
        city_input.setText("");
//        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
        if (city.equals(" ")) {
            Toast.makeText(MainActivity.this, "EMPTY CITY", Toast.LENGTH_SHORT).show();
        }
        weatherhttp(city);

    }

    public void music_sugg() {
        Intent intent2 = new Intent(this, MainActivity2.class);
        Toast.makeText(this,"ARTISTLIST",Toast.LENGTH_SHORT).show();
        intent2.putExtra("Artist List", artist_list);
        intent2.putExtra("Song List", song_list);
        startActivity(intent2);
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
        String url = base_url + city + "&appid=" + api_key;
//        Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                double double_long = 0;
                double double_lat = 0;
                double temp = 0;
                String weather_description = "";
                int humidity = 0;
                String icon_code = "";
                String wind_speed = "";
                String cloudiness = "";
                try {
                    JSONObject coordobj = response.getJSONObject("coord");
                    String lat = coordobj.getString("lat");
                    String lon = coordobj.getString("lon");
                    double_lat = Double.parseDouble(lat);
                    double_long = Double.parseDouble(lon);
                    String map3 = String.valueOf(map);
                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject weatherobj = jsonArray.getJSONObject(0);
                    weather_description = weatherobj.getString("description");
                    icon_code = weatherobj.getString("icon");
                    JSONObject mainobj = response.getJSONObject("main");
                    temp = Math.round(mainobj.getDouble("temp") - 273.15);
                    humidity = mainobj.getInt("humidity");
                    JSONObject windobj = response.getJSONObject("wind");
                    JSONObject cloudobj = response.getJSONObject("clouds");
                    cloudiness = cloudobj.getString("all");
                    wind_speed = windobj.getString("speed");
                    JSONObject sysobj = response.getJSONObject("sys");
                    String Country = response.getString("country");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    map_recenter(double_lat, double_long);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView city_text = (TextView) findViewById(R.id.City);
                city_text.setText(city);
                TextView temp_text = (TextView) findViewById(R.id.Temp);
                temp_text.setText("Temperature: " + String.valueOf(temp + "\u00B0") + "c");
                TextView description_text = (TextView) findViewById(R.id.Description);
                description_text.setText(weather_description);
                TextView humidity_text = (TextView) findViewById(R.id.Humidity);
                humidity_text.setText("Humidity: " + humidity + "%");
                get_icon(icon_code);
                TextView wind_speed_text = (TextView) findViewById(R.id.Wind);
                wind_speed_text.setText("Wind Speed: " + wind_speed + "m/s");
//                Toast.makeText(MainActivity.this, wind_speed + "WINDDD", Toast.LENGTH_SHORT).show();
                TextView cloud_text = (TextView) findViewById(R.id.Clouds);
                cloud_text.setText("Cloudiness: " + cloudiness + "%");
//                spotify_playlist(description);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        requestqueue.add(jsonObjectRequest);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void map_recenter(double lat, double lon) throws JSONException {
        map.getController().animateTo(new GeoPoint(lat, lon));
        Marker mark = new Marker(map);
        mark.setPosition(new GeoPoint(lat, lon));
        mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mark.setIcon(getResources().getDrawable(R.drawable.ic_marker_foreground));
        map.getOverlays().add(mark);
        map.getController().animateTo(new GeoPoint(lat, lon));
        marker_list.add(mark);
        if (marker_list.size()!=1) {
            marker_list.get(0).remove(map);
            marker_list.remove(0);
        }
        mark.setInfoWindow(null);
        mapController.setZoom(15);
    }
    public void get_icon(String icon_code) {
//        Toast.makeText(getApplicationContext(), icon_code, Toast.LENGTH_SHORT).show();
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        String icon_owp_url = "https://openweathermap.org/img/wn/" + icon_code + "@2x.png";
        int max_width = 300;
        int max_height = 300;
        ImageRequest imageRequest = new ImageRequest(icon_owp_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ImageView imageView = (ImageView) findViewById(R.id.icon);
                imageView.setImageBitmap(response);
            }
        }, max_width, max_height, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("image_Error", String.valueOf(error));
                        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestqueue.add(imageRequest);

    }

    public void spotify_playlist(String token) {
        Toast.makeText(MainActivity.this,"MAIN IN SPOTIFY PPLAYLIST"+main_string, Toast.LENGTH_SHORT).show();
        // Replace weather_description with main object
        if (Objects.equals(main_string, "Drizzle") || Objects.equals(main_string, "Rain") || Objects.equals(main_string, "Thunderstorm")) {
            Toast.makeText(this, "INSODE CONDITION", Toast.LENGTH_SHORT).show();
            String playlist_url = "https://api.spotify.com/v1/playlists/1VOREp7qG3Jen3Mpgdus41/tracks";
            JsonObjectRequest JSONRequest = new JsonObjectRequest(playlist_url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Dictionary<String, String> tracks_dict = new Hashtable<String, String>();
                    JSONArray items = new JSONArray();
                    JSONObject items_json = null;
                    JSONObject track = null;
                    JSONArray artists_array = null;
                    JSONObject artists_jsonobject = null;
                    JSONArray items_elements = null;
                    String artist_name = "";
                    String song = "";
                    JSONArray track_dict_elements = null;
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH RESPONSE" + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY RESPONSE", response.toString());
                    Log.d("Key", String.valueOf(response.names()));
                    try {
                        items = response.getJSONArray("items");
                        for (int i = 0; i < 32; i++) {
                            items_json = items.getJSONObject(i);
                            track = items_json.getJSONObject("track");
                            artists_array = track.getJSONArray("artists");
                            artists_jsonobject = artists_array.getJSONObject(0);
                            artist_name = artists_jsonobject.getString("name");
                            artist_list.add(artist_name);
                            song = track.getString("name");
                            song_list.add(song);
                            tracks_dict.put(song, artist_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("Artists List", String.valueOf(artist_list));
                    Log.d("check", new String("check"));
                    Log.d("ITEMS JSON", String.valueOf(items_json));
                    Log.d("TRACK", String.valueOf(track));
                    Log.d("Artists Array", String.valueOf(artists_array));
                    Log.d("Artists dict", String.valueOf(artists_jsonobject));
                    Log.d("Artist name", artist_name);
                    Log.d("SONG", song);
                    Log.d("Song list", String.valueOf(song_list));
                    Log.d("tracks_dict", String.valueOf(tracks_dict));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY AUTH Error: ", error.toString());
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            requestqueue.add(JSONRequest);
        }

        if (Objects.equals(main_string, "Clear")) {
            Toast.makeText(MainActivity.this,"INSIDE CLEAR", Toast.LENGTH_SHORT).show();
            Log.d("INSIDE MIST", "mistttt");
            String playlist_url = "https://api.spotify.com/v1/playlists/1ioN4Mwrv6G7DGP2indDqK/tracks";
            JsonObjectRequest JSONRequest = new JsonObjectRequest(playlist_url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Dictionary<String, String> tracks_dict = new Hashtable<String, String>();
                    JSONArray items = new JSONArray();
                    JSONObject items_json = null;
                    JSONObject track = null;
                    JSONArray artists_array = null;
                    JSONObject artists_jsonobject = null;
                    JSONArray items_elements = null;
                    String artist_name = "";
                    String song = "";
                    JSONArray track_dict_elements = null;
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH RESPONSE" + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY RESPONSE", response.toString());
                    Log.d("Key", String.valueOf(response.names()));
                    try {
                        items = response.getJSONArray("items");
                        for (int i = 0; i < 657; i++) {
                            items_json = items.getJSONObject(i);
                            track = items_json.getJSONObject("track");
                            artists_array = track.getJSONArray("artists");
                            artists_jsonobject = artists_array.getJSONObject(0);
                            artist_name = artists_jsonobject.getString("name");
                            artist_list.add(artist_name);
                            song = track.getString("name");
                            song_list.add(song);
                            tracks_dict.put(song, artist_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("Artists List", String.valueOf(artist_list));
                    Log.d("check", new String("check"));
                    Log.d("ITEMS JSON", String.valueOf(items_json));
                    Log.d("TRACK", String.valueOf(track));
                    Log.d("Artists Array", String.valueOf(artists_array));
                    Log.d("Artists dict", String.valueOf(artists_jsonobject));
                    Log.d("Artist name", artist_name);
                    Log.d("SONG", song);
                    Log.d("Song list", String.valueOf(song_list));
                    Log.d("tracks_dict", String.valueOf(tracks_dict));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY AUTH Error: ", error.toString());
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            requestqueue.add(JSONRequest);
        }

        if(Objects.equals(main_string, "Clouds")) {
            String playlist_url = "https://api.spotify.com/v1/playlists/6RQviMFQJQZHPiefQYJMfL/tracks";
            JsonObjectRequest JSONRequest = new JsonObjectRequest(playlist_url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Dictionary<String, String> tracks_dict = new Hashtable<String, String>();
                    JSONArray items = new JSONArray();
                    JSONObject items_json = null;
                    JSONObject track = null;
                    JSONArray artists_array = null;
                    JSONObject artists_jsonobject = null;
                    JSONArray items_elements = null;
                    String artist_name = "";
                    String song = "";
                    JSONArray track_dict_elements = null;
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH RESPONSE" + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY RESPONSE", response.toString());
                    Log.d("Key", String.valueOf(response.names()));
                    try {
                        items = response.getJSONArray("items");
                        for (int i = 0; i < 89; i++) {
                            items_json = items.getJSONObject(i);
                            track = items_json.getJSONObject("track");
                            artists_array = track.getJSONArray("artists");
                            artists_jsonobject = artists_array.getJSONObject(0);
                            artist_name = artists_jsonobject.getString("name");
                            artist_list.add(artist_name);
                            song = track.getString("name");
                            song_list.add(song);
                            tracks_dict.put(song, artist_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("Artists List", String.valueOf(artist_list));
                    Log.d("check", new String("check"));
                    Log.d("ITEMS JSON", String.valueOf(items_json));
                    Log.d("TRACK", String.valueOf(track));
                    Log.d("Artists Array", String.valueOf(artists_array));
                    Log.d("Artists dict", String.valueOf(artists_jsonobject));
                    Log.d("Artist name", artist_name);
                    Log.d("SONG", song);
                    Log.d("Song list", String.valueOf(song_list));
                    Log.d("tracks_dict", String.valueOf(tracks_dict));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY AUTH Error: ", error.toString());
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            requestqueue.add(JSONRequest);
        }

        if(Objects.equals(main_string, "Mist")) {
            String playlist_url = "https://api.spotify.com/v1/playlists/5oe6cUg1iDPpsIcdWzJV7M/tracks";
            JsonObjectRequest JSONRequest = new JsonObjectRequest(playlist_url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Dictionary<String, String> tracks_dict = new Hashtable<String, String>();
                    JSONArray items = new JSONArray();
                    JSONObject items_json = null;
                    JSONObject track = null;
                    JSONArray artists_array = null;
                    JSONObject artists_jsonobject = null;
                    JSONArray items_elements = null;
                    String artist_name = "";
                    String song = "";
                    JSONArray track_dict_elements = null;
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH RESPONSE" + response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY RESPONSE", response.toString());
                    Log.d("Key", String.valueOf(response.names()));
                    try {
                        items = response.getJSONArray("items");
                        for (int i = 0; i < 18; i++) {
                            items_json = items.getJSONObject(i);
                            track = items_json.getJSONObject("track");
                            artists_array = track.getJSONArray("artists");
                            artists_jsonobject = artists_array.getJSONObject(0);
                            artist_name = artists_jsonobject.getString("name");
                            artist_list.add(artist_name);
                            song = track.getString("name");
                            song_list.add(song);
                            tracks_dict.put(song, artist_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("Artists List", String.valueOf(artist_list));
                    Log.d("check", new String("check"));
                    Log.d("ITEMS JSON", String.valueOf(items_json));
                    Log.d("TRACK", String.valueOf(track));
                    Log.d("Artists Array", String.valueOf(artists_array));
                    Log.d("Artists dict", String.valueOf(artists_jsonobject));
                    Log.d("Artist name", artist_name);
                    Log.d("SONG", song);
                    Log.d("Song list", String.valueOf(song_list));
                    Log.d("tracks_dict", String.valueOf(tracks_dict));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "SPOTIFY AUTH Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("SPOTIFY AUTH Error: ", error.toString());
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + token);
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            requestqueue.add(JSONRequest);

        }
    }


    public void spotify_authorization() {
        AuthorizationRequest request = new AuthorizationRequest.Builder(client_id, AuthorizationResponse.Type.TOKEN, redirect_uri)
                .setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"})
                .build();

        AuthorizationClient.openLoginActivity(this, request_code, request);

    }protected void onActivityResult(int requestCode,int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == request_code) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    String token = response.getAccessToken();
//                    Toast.makeText(MainActivity.this,"TOKEN: "+token, Toast.LENGTH_SHORT).show();
//                    Log.d("TOKEN", token);
                    spotify_playlist(token);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean singleTapConfirmedHelper (GeoPoint p){
//            Toast.makeText(getApplicationContext(), "LAT" + p.getLatitude() + "LONG" + p.getLatitude(), Toast.LENGTH_SHORT).show();
        String lat = String.valueOf(p.getLatitude());
        String lon = String.valueOf(p.getLongitude());
        Marker mark = new Marker(map);
        mark.setPosition(p);
        mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mark.setIcon(getResources().getDrawable(R.drawable.ic_marker_foreground));
        map.getOverlays().add(mark);
        map.getController().animateTo(p);
        marker_list.add(mark);
        if (marker_list.size()!=1) {
            marker_list.get(0).remove(map);
            marker_list.remove(0);
        }
        mark.setInfoWindow(null);
        mapController.setZoom(15);

        String closest_city_url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + api_key;
        JsonObjectRequest clicked_json = new JsonObjectRequest(closest_city_url, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                String city = null;
                double temp = 0;
                String weather_description = null;
                int humidity = 0;
                String icon_code = null;
                String wind_speed = null;
                String cloudiness = null;
                try {
                    JSONArray weather_array = response.getJSONArray("weather");
                    JSONObject weatherobj = weather_array.getJSONObject(0);
                    weather_description = weatherobj.getString("description");
                    icon_code = weatherobj.getString("icon");
                    JSONObject mainobj = response.getJSONObject("main");
                    temp = Math.round(mainobj.getDouble("temp") - 273.15);
                    humidity = mainobj.getInt("humidity");
                    JSONObject windobj = response.getJSONObject("wind");
                    JSONObject cloudobj = response.getJSONObject("clouds");
                    cloudiness = cloudobj.getString("all");
                    wind_speed = windobj.getString("speed");
                    city = response.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView city_text = (TextView) findViewById(R.id.City);
                city_text.setText(city);
                TextView temp_text = (TextView) findViewById(R.id.Temp);
                temp_text.setText("Temperature: " + String.valueOf(temp + "\u00B0") + "c");
                TextView description_text = (TextView) findViewById(R.id.Description);
                description_text.setText(weather_description);
                TextView humidity_text = (TextView) findViewById(R.id.Humidity);
                humidity_text.setText("Humidity: " + humidity + "%");
                get_icon(icon_code);
                TextView wind_speed_text = (TextView) findViewById(R.id.Wind);
                wind_speed_text.setText("Wind Speed: " + wind_speed + "m/s");
//                    Toast.makeText(MainActivity.this, wind_speed + "WINDDD", Toast.LENGTH_SHORT).show();
                TextView cloud_text = (TextView) findViewById(R.id.Clouds);
                cloud_text.setText("Cloudiness: " + cloudiness + "%");
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError error){
                Toast.makeText(getApplicationContext(), "Error Click: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        requestqueue.add(clicked_json);
        InfoWindow.closeAllInfoWindowsOn(map);
        return true;
    }

    @Override
    public boolean longPressHelper (GeoPoint p){
        Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_SHORT).show();
        return false;
    }



}