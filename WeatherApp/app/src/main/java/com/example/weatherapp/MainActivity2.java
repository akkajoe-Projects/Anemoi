package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public String music_response = "";
    public ArrayList<String> artist_list= new ArrayList<String>();
    public ArrayList<String> song_list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent =getIntent();
        artist_list = intent.getStringArrayListExtra("Artist List");
        song_list = intent.getStringArrayListExtra("Song List");
        music_slots();
    }

public void music_slots() {
        TextView Heading = (TextView) findViewById(R.id.Activity2Head);

        TextView song1 = (TextView) findViewById(R.id.song1);
        TextView song2 = (TextView) findViewById(R.id.Song2);
        TextView song3 = (TextView) findViewById(R.id.Song3);
        TextView song4 = (TextView) findViewById(R.id.Song4);
        TextView song5 = (TextView) findViewById(R.id.Song5);
        TextView song6 = (TextView) findViewById(R.id.Song6);
        TextView song7 = (TextView) findViewById(R.id.Song7);
        TextView song8 = (TextView) findViewById(R.id.Song8);
        TextView song9 = (TextView) findViewById(R.id.Song9);
        TextView song10 = (TextView) findViewById(R.id.Song10);
        TextView song11 = (TextView) findViewById(R.id.Song11);
        TextView song12 = (TextView) findViewById(R.id.Song12);
        TextView song13 = (TextView) findViewById(R.id.Song13);
        TextView song14 = (TextView) findViewById(R.id.Song14);
        TextView song15 = (TextView) findViewById(R.id.Song15);

        TextView artist1 = (TextView) findViewById(R.id.artist1);
        TextView artist2 = (TextView) findViewById(R.id.artist2);
        TextView artist3 = (TextView) findViewById(R.id.Artist3);
        TextView artist4 = (TextView) findViewById(R.id.Artist4);
        TextView artist5 = (TextView) findViewById(R.id.Artist5);
        TextView artist6 = (TextView) findViewById(R.id.Artist6);
        TextView artist7 = (TextView) findViewById(R.id.Artist7);
        TextView artist8 = (TextView) findViewById(R.id.Artist8);
        TextView artist9 = (TextView) findViewById(R.id.Artist9);
        TextView artist10 = (TextView) findViewById(R.id.Artist10);
        TextView artist11 = (TextView) findViewById(R.id.Artist11);
        TextView artist12 = (TextView) findViewById(R.id.Artist12);
        TextView artist13 = (TextView) findViewById(R.id.Artist13);
        TextView artist14 = (TextView) findViewById(R.id.Artist14);
        TextView artist15 = (TextView) findViewById(R.id.Artist15);

        song1.setText(song_list.get(0));
        song2.setText(song_list.get(1));
        song3.setText(song_list.get(2));
        song4.setText(song_list.get(3));
        song5.setText(song_list.get(4));
        song6.setText(song_list.get(5));
        song7.setText(song_list.get(6));
        song8.setText(song_list.get(7));
        song9.setText(song_list.get(8));
        song10.setText(song_list.get(9));
        song11.setText(song_list.get(10));
        song12.setText(song_list.get(11));
        song13.setText(song_list.get(12));
        song14.setText(song_list.get(13));
        song15.setText(song_list.get(14));

        artist1.setText(artist_list.get(0));
        artist2.setText(artist_list.get(1));
        artist3.setText(artist_list.get(2));
        artist4.setText(artist_list.get(3));
        artist5.setText(artist_list.get(4));
        artist6.setText(artist_list.get(5));
        artist7.setText(artist_list.get(6));
        artist8.setText(artist_list.get(7));
        artist9.setText(artist_list.get(8));
        artist10.setText(artist_list.get(9));
        artist11.setText(artist_list.get(10));
        artist12.setText(artist_list.get(11));
        artist13.setText(artist_list.get(12));
        artist14.setText(artist_list.get(13));
        artist15.setText(artist_list.get(14));

}




}
