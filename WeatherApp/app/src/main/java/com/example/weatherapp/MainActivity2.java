package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
        music_moods(Heading, song1, song2, song3, song4, song5, song6, song7, song8,
                song9, song10, song11, song12, song13, song14, song15);

}


    @SuppressLint("SetTextI18n")
public void music_moods(TextView Heading, TextView song1, TextView song2,
                        TextView song3, TextView song4, TextView song5, TextView song6,
                        TextView song7, TextView song8, TextView song9, TextView song10,
                        TextView song11, TextView song12, TextView song13, TextView song14, TextView song15) {

            if (Objects.equals(main_string, "Rain") || Objects.equals(main_string, "Drizzle")
                    || Objects.equals(main_string, "Thunderstorm") || Objects.equals(main_string, "Haze"))  {
                Heading.setText("Rainy Day Muse");
                RelativeLayout Background = findViewById(R.id.Bg);
                Background.setBackgroundColor(getResources().getColor(R.color.WeldonBlue));
                Background.setAlpha((float) 0.6);
//                try {
//                ImageView sunny_icon = findViewById(R.id.sunnyzone);
//                sunny_icon.setImageDrawable(null);
//                    ImageView ballet_icon = findViewById(R.id.ballet);
//                    ballet_icon.setImageDrawable(null);} catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            if (Objects.equals(main_string, "Clear")){
                Heading.setText("Sunny Tunes");
                RelativeLayout Background = findViewById(R.id.Bg);
                ImageView singing_woman = findViewById(R.id.SingingWoman);
                singing_woman.setImageDrawable(null);
                ImageView sunny_icon = new ImageView(MainActivity2.this);
                sunny_icon.setImageResource(R.drawable.zone);
                sunny_icon.setId(R.id.sunnyzone);
                RelativeLayout RelativeLayout = findViewById(R.id.RelativeLayout);
                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(600,600);
                params.setMargins(500,110,0,0);
                sunny_icon.setLayoutParams(params);
                RelativeLayout.addView(sunny_icon);
                GifImageView rain_gif= findViewById(R.id.rain_gif);
                rain_gif.setImageDrawable(null);
                rain_gif.setBackgroundResource(R.drawable.skysun);
                rain_gif.setAlpha((float) 0.15);
                Background.setBackgroundColor(getResources().getColor(R.color.MinionYellow));
                Background.setAlpha((float) 0.9);
                Heading.setTextColor(getResources().getColor(R.color.Teal));
                song1.setTextColor(getResources().getColor(R.color.Teal));
                song2.setTextColor(getResources().getColor(R.color.Teal));
                song3.setTextColor(getResources().getColor(R.color.Teal));
                song4.setTextColor(getResources().getColor(R.color.Teal));
                song5.setTextColor(getResources().getColor(R.color.Teal));
                song6.setTextColor(getResources().getColor(R.color.Teal));
                song7.setTextColor(getResources().getColor(R.color.Teal));
                song8.setTextColor(getResources().getColor(R.color.Teal));
                song9.setTextColor(getResources().getColor(R.color.Teal));
                song10.setTextColor(getResources().getColor(R.color.Teal));
                song11.setTextColor(getResources().getColor(R.color.Teal));
                song12.setTextColor(getResources().getColor(R.color.Teal));
                song13.setTextColor(getResources().getColor(R.color.Teal));
                song14.setTextColor(getResources().getColor(R.color.Teal));
                song15.setTextColor(getResources().getColor(R.color.Teal));

//                try {
//                    ImageView ballet_icon = findViewById(R.id.ballet);
//                    ballet_icon.setImageDrawable(null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }

            if (Objects.equals(main_string, "Haze") || Objects.equals(main_string, "Mist")) {
                Heading.setText("Foggy Euphonies");
                RelativeLayout Background = findViewById(R.id.Bg);
                Background.setBackgroundColor(getResources().getColor(R.color.MinionYellow));
                GifImageView rain_gif= findViewById(R.id.rain_gif);
                rain_gif.setImageDrawable(null);
                ImageView singing_woman = findViewById(R.id.SingingWoman);
                singing_woman.setImageDrawable(null);
//                try {
//                    ImageView sunny_icon = findViewById(R.id.sunnyzone);
//                    sunny_icon.setImageDrawable(null);} catch (Exception e) {
//                    e.printStackTrace();
//                    ImageView ballet_icon = findViewById(R.id.ballet);
//                    ballet_icon.setImageDrawable(null);
//                }
                rain_gif.setImageDrawable(null);
                rain_gif.setBackgroundResource(R.drawable.foggygif);
                Background.setBackgroundColor(getResources().getColor(R.color.DirtyWhite));
                Background.setAlpha((float) 0.9);
                Heading.setTextColor(getResources().getColor(R.color.Teal));
                song1.setTextColor(getResources().getColor(R.color.Teal));
                song2.setTextColor(getResources().getColor(R.color.Teal));
                song3.setTextColor(getResources().getColor(R.color.Teal));
                song4.setTextColor(getResources().getColor(R.color.Teal));
                song5.setTextColor(getResources().getColor(R.color.Teal));
                song6.setTextColor(getResources().getColor(R.color.Teal));
                song7.setTextColor(getResources().getColor(R.color.Teal));
                song8.setTextColor(getResources().getColor(R.color.Teal));
                song9.setTextColor(getResources().getColor(R.color.Teal));
                song10.setTextColor(getResources().getColor(R.color.Teal));
                song11.setTextColor(getResources().getColor(R.color.Teal));
                song12.setTextColor(getResources().getColor(R.color.Teal));
                song13.setTextColor(getResources().getColor(R.color.Teal));
                song14.setTextColor(getResources().getColor(R.color.Teal));
                song15.setTextColor(getResources().getColor(R.color.Teal));
            }

            if (Objects.equals(main_string, "Clouds")) {
                Heading.setText("Sound Cloud");
                RelativeLayout Background = findViewById(R.id.Bg);
                Background.setBackgroundColor(getResources().getColor(R.color.MinionYellow));
                GifImageView rain_gif= findViewById(R.id.rain_gif);
                rain_gif.setImageDrawable(null);
                ImageView singing_woman = findViewById(R.id.SingingWoman);
                singing_woman.setImageDrawable(null);
                rain_gif.setImageDrawable(null);
                rain_gif.setBackgroundResource(R.drawable.cloudy);
                Background.setBackgroundColor(getResources().getColor(R.color.DirtyWhite));
                Background.setAlpha((float) 0.99);
                ImageView ballet_icon = new ImageView(MainActivity2.this);
                ballet_icon.setImageResource(R.drawable.ballet);
                ballet_icon.setId(R.id.ballet);
                RelativeLayout RelativeLayout = findViewById(R.id.RelativeLayout);
                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,500);
                params.setMargins(560,170,0,0);
                ballet_icon.setLayoutParams(params);
                RelativeLayout.addView(ballet_icon);
                Heading.setTextColor(getResources().getColor(R.color.Teal));
                song1.setTextColor(getResources().getColor(R.color.Teal));
                song2.setTextColor(getResources().getColor(R.color.Teal));
                song3.setTextColor(getResources().getColor(R.color.Teal));
                song4.setTextColor(getResources().getColor(R.color.Teal));
                song5.setTextColor(getResources().getColor(R.color.Teal));
                song6.setTextColor(getResources().getColor(R.color.Teal));
                song7.setTextColor(getResources().getColor(R.color.Teal));
                song8.setTextColor(getResources().getColor(R.color.Teal));
                song9.setTextColor(getResources().getColor(R.color.Teal));
                song10.setTextColor(getResources().getColor(R.color.Teal));
                song11.setTextColor(getResources().getColor(R.color.Teal));
                song12.setTextColor(getResources().getColor(R.color.Teal));
                song13.setTextColor(getResources().getColor(R.color.Teal));
                song14.setTextColor(getResources().getColor(R.color.Teal));
                song15.setTextColor(getResources().getColor(R.color.Teal));
                try {
                    ImageView sunny_icon = findViewById(R.id.sunnyzone);
                    sunny_icon.setImageDrawable(null);} catch (Exception e) {
                    e.printStackTrace();
                }

            }



}
}
