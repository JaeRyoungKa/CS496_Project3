package com.project.cs496.weather_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.vision.text.Text;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    private String fb_id;
    private String fb_token;
    String sfName = "myFile";
    private double latitude;
    private double longitude;
    private String place_name;

    TextView location_box;
    TextView temp_box;
    TextView description_box;

    TextView et6;
    TextView et7;
    TextView et8;
    TextView et9;
    TextView et10;
    TextView et11;
    TextView et12;
    TextView et13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF666));

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        String latitude = sf.getString("latitude", ""); // 키값으로 꺼냄
        String longitude = sf.getString("longitude", "");
        place_name = sf.getString("place_name","");

        Log.i("latitude",latitude);
        Log.i("longitude",longitude);

        location_box = (TextView) findViewById(R.id.location);

        temp_box = (TextView) findViewById(R.id.temperature);
        description_box = (TextView) findViewById(R.id.detail);

        et6 = (TextView) findViewById(R.id.temp_00);
        et7 = (TextView) findViewById(R.id.temp_03);
        et8 = (TextView) findViewById(R.id.temp_06);
        et9 = (TextView) findViewById(R.id.temp_09);
        et10 = (TextView) findViewById(R.id.temp_12);
        et11 = (TextView) findViewById(R.id.temp_15);
        et12 = (TextView) findViewById(R.id.temp_18);
        et13 = (TextView) findViewById(R.id.temp_21);

        location_box.setText(place_name);

         Ion.with(this)
                 .load((new WeatherHttpClient()).getWeatherDataUrl(latitude, longitude))
                 .asJsonObject()
                 .setCallback(new FutureCallback<JsonObject>() {
                     @Override
                     public void onCompleted(Exception e, JsonObject result) {
                         JsonObject main = result.getAsJsonObject("main");
                         String current_temperature = main.get("temp").toString();
                         temp_box.setText(current_temperature);
                         // main --> object 중 temp 항목 빼오는 과정 그리고 setText함

                         JsonObject weather = result.getAsJsonArray("weather").get(0).getAsJsonObject();
                         String description = weather.get("description").getAsString();
                         description_box.setText(description);
                     }
                 });

        Log.i("url!",(new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude));
        Ion.with(this)
                .load((new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray list = result.getAsJsonArray("list");

                        et6.setText(list.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et7.setText(list.get(1).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et8.setText(list.get(2).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et9.setText(list.get(3).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et10.setText(list.get(4).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et11.setText(list.get(5).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et12.setText(list.get(6).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et13.setText(list.get(7).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        String latitude = sf.getString("latitude", ""); // 키값으로 꺼냄
        String longitude = sf.getString("longitude", "");
        place_name = sf.getString("place_name","");



        location_box.setText(place_name);

        Ion.with(this)
                .load((new WeatherHttpClient()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonObject main = result.getAsJsonObject("main");
                        String current_temperature = main.get("temp").toString();
                        temp_box.setText(current_temperature);
                        // main --> object 중 temp 항목 빼오는 과정 그리고 setText함

                        JsonObject weather = result.getAsJsonArray("weather").get(0).getAsJsonObject();
                        String description = weather.get("description").getAsString();
                        description_box.setText(description);
                    }
                });

        Log.i("url!",(new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude));
        Ion.with(this)
                .load((new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray list = result.getAsJsonArray("list");

                        et6.setText(list.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et7.setText(list.get(1).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et8.setText(list.get(2).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et9.setText(list.get(3).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et10.setText(list.get(4).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et11.setText(list.get(5).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et12.setText(list.get(6).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                        et13.setText(list.get(7).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString());
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.fb_login_button) {
            Intent fb_login_intent = new Intent(this, LoginActivity.class);
            startActivityForResult(fb_login_intent, 1);
            return true;
        }

        if(id == R.id.travel_button) {
            Intent setting_intent = new Intent(this,TravelActivity.class);
            startActivityForResult(setting_intent,4);
            return true;
        }

        if(id == R.id.setting_button) {
            Intent setting_intent = new Intent(this,SettingActivity.class);
            startActivityForResult(setting_intent,2);
            return true;
        }

        if(id == R.id.map_button) {
            Intent gotomap = new Intent(this, GPSandGoogleMap.class);
            startActivityForResult(gotomap, 3);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                fb_id = data.getStringExtra("user_id");
                fb_token = data.getStringExtra("user_token");
                Log.d("id!",fb_id);
            } else {
                fb_id = "";
                fb_token = "";
            }
        } else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK) {
                latitude = data.getDoubleExtra("latitude",0.00);
                longitude = data.getDoubleExtra("longitude",0.00);
                place_name = data.getStringExtra("place_name");
                SharedPreferences sf = getSharedPreferences(sfName,0);
                SharedPreferences.Editor editor = sf.edit();

                editor.putString("latitude",Double.toString(latitude));
                editor.putString("longitude",Double.toString(longitude));
                editor.putString("place_name",place_name);

                editor.commit();

          //      Log.i("latitude",String.valueOf(latitude));
           //     Log.i("longitude",String.valueOf(longitude));
            //    Log.i("place_name",place_name);

            } else {
                latitude = 0.00;
                longitude = 0.00;
                place_name = "";
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
