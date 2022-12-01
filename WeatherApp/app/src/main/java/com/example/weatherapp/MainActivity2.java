package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity2 extends AppCompatActivity {
    public String music_response = "";
    public ArrayList<String> artist_list= new ArrayList<String>();
    public ArrayList<String> song_list = new ArrayList<String>();
    public String main_string = "";
    public String weather_description = "";
    public GifImageView rain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent =getIntent();
        artist_list = intent.getStringArrayListExtra("Artist List");
        song_list = intent.getStringArrayListExtra("Song List");
        main_string = intent.getStringExtra("Main String");
        weather_description = intent.getStringExtra("Weather Description");
        music_slots();
    }

@SuppressLint("SetTextI18n")
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


        song1.setText(song_list.get(0)+", "+artist_list.get(0));
        song2.setText(song_list.get(1)+", "+artist_list.get(1));
        song3.setText(song_list.get(2)+", "+artist_list.get(2));
        song4.setText(song_list.get(3)+", "+artist_list.get(3));
        song5.setText(song_list.get(4)+", "+artist_list.get(4));
        song6.setText(song_list.get(5)+", "+artist_list.get(5));
        song7.setText(song_list.get(6)+", "+artist_list.get(6));
        song8.setText(song_list.get(7)+", "+artist_list.get(7));
        song9.setText(song_list.get(8)+", "+artist_list.get(8));
        song10.setText(song_list.get(9)+", "+artist_list.get(9));
        song11.setText(song_list.get(10)+", "+artist_list.get(10));
        song12.setText(song_list.get(11)+", "+artist_list.get(11));
        song13.setText(song_list.get(12)+", "+artist_list.get(12));
        song14.setText(song_list.get(13)+", "+artist_list.get(13));
        song15.setText(song_list.get(14)+", "+artist_list.get(14));
        music_moods(Heading);

}

@SuppressLint("SetTextI18n")
public void music_moods(TextView Heading) {

            if (Objects.equals(main_string, "Rain") || Objects.equals(main_string, "Drizzle") || Objects.equals(main_string, "Thunderstorm")
                    || Objects.equals(main_string, "Haze"))  {
                Heading.setText("Rainy Day Muse");
                RelativeLayout Background = findViewById(R.id.Bg);
                Background.setBackgroundColor(getResources().getColor(R.color.WeldonBlue));
                Background.setAlpha((float) 0.6);
            }

            if (Objects.equals(main_string, "Clear") || Objects.equals(main_string, "Mist")
                    || Objects.equals(main_string, "Clouds")){
                Heading.setText("Sunny Tunes");
                RelativeLayout Background = findViewById(R.id.Bg);
                Background.setBackgroundColor(getResources().getColor(R.color.MinionYellow));
                GifImageView rain_gif= findViewById(R.id.rain_gif);
                rain_gif.setImageDrawable(null);
                ImageView singing_woman = findViewById(R.id.SingingWoman);
                singing_woman.setImageDrawable(null);
            }
}
}
