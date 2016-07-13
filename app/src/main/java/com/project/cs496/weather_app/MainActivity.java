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

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String nowWeatherData = ((new WeatherHttpClient()).getWeatherData(latitude, longitude));
        String futureWeatherData = ((new WeatherHttpClient2()).getWeatherData(latitude, longitude));

        Log.i("data!",nowWeatherData);


        try {
            JSONObject reader = new JSONObject(nowWeatherData);
            JSONObject reader2 = new JSONObject(futureWeatherData);

            JSONArray list = reader2.getJSONArray("list");
            JSONObject main = reader.getJSONObject("main");

            JSONArray weather = reader.getJSONArray("weather");
            JSONObject des = weather.getJSONObject(0);
            JSONObject th1 = list.getJSONObject(0);
            JSONObject thd1 = th1.getJSONObject("main");

            String temp = main.getString("temp");
            TextView temp_box = (TextView) findViewById(R.id.temperature);
            temp_box.setText(temp);

            TextView location_box = (TextView) findViewById(R.id.location);
            location_box.setText(place_name);

            TextView et5 = (TextView) findViewById(R.id.detail);
            String temp3 = des.getString("description");
            et5.setText(temp3);

            TextView et6 = (TextView) findViewById(R.id.temp_00);
            String temp4 = thd1.getString("temp");
            et6.setText(temp4);

            TextView et7 = (TextView) findViewById(R.id.temp_03);
            et7.setText(list.getJSONObject(1).getJSONObject("main").getString("temp"));

            TextView et8 = (TextView) findViewById(R.id.temp_06);
            et8.setText(list.getJSONObject(2).getJSONObject("main").getString("temp"));

            TextView et9 = (TextView) findViewById(R.id.temp_09);
            et9.setText(list.getJSONObject(3).getJSONObject("main").getString("temp"));

            TextView et10 = (TextView) findViewById(R.id.temp_12);
            et10.setText(list.getJSONObject(4).getJSONObject("main").getString("temp"));

            TextView et11 = (TextView) findViewById(R.id.temp_15);
            et11.setText(list.getJSONObject(5).getJSONObject("main").getString("temp"));

            TextView et12 = (TextView) findViewById(R.id.temp_18);
            et12.setText(list.getJSONObject(6).getJSONObject("main").getString("temp"));

            TextView et13 = (TextView) findViewById(R.id.temp_21);
            et13.setText(list.getJSONObject(7).getJSONObject("main").getString("temp"));

        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }

       /* FacebookSdk.sdkInitialize(getApplicationContext());

        if(AccessToken.getCurrentAccessToken() == null) {
            Intent intent_to_login = new Intent(this, LoginActivity.class);
            startActivityForResult(intent_to_login,1);
        } else {
            fb_id = AccessToken.getCurrentAccessToken().getUserId();
            fb_token = AccessToken.getCurrentAccessToken().getToken();
        }

*/
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        String latitude = sf.getString("latitude", ""); // 키값으로 꺼냄
        String longitude = sf.getString("longitude", "");
        place_name = sf.getString("place_name","");

        Log.i("latitude",latitude);
        Log.i("longitude",longitude);

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String nowWeatherData = ((new WeatherHttpClient()).getWeatherData(latitude, longitude));
        String futureWeatherData = ((new WeatherHttpClient2()).getWeatherData(latitude, longitude));

        Log.i("data!",nowWeatherData);


        try {
            JSONObject reader = new JSONObject(nowWeatherData);
            JSONObject reader2 = new JSONObject(futureWeatherData);

            JSONArray list = reader2.getJSONArray("list");
            JSONObject main = reader.getJSONObject("main");

            JSONArray weather = reader.getJSONArray("weather");
            JSONObject des = weather.getJSONObject(0);
            JSONObject th1 = list.getJSONObject(0);
            JSONObject thd1 = th1.getJSONObject("main");

            String temp = main.getString("temp");
            TextView temp_box = (TextView) findViewById(R.id.temperature);
            temp_box.setText(temp);

            TextView location_box = (TextView) findViewById(R.id.location);
            location_box.setText(place_name);

            TextView et5 = (TextView) findViewById(R.id.detail);
            String temp3 = des.getString("description");
            et5.setText(temp3);

            TextView et6 = (TextView) findViewById(R.id.temp_00);
            String temp4 = thd1.getString("temp");
            et6.setText(temp4);

            TextView et7 = (TextView) findViewById(R.id.temp_03);
            et7.setText(list.getJSONObject(1).getJSONObject("main").getString("temp"));

            TextView et8 = (TextView) findViewById(R.id.temp_06);
            et8.setText(list.getJSONObject(2).getJSONObject("main").getString("temp"));

            TextView et9 = (TextView) findViewById(R.id.temp_09);
            et9.setText(list.getJSONObject(3).getJSONObject("main").getString("temp"));

            TextView et10 = (TextView) findViewById(R.id.temp_12);
            et10.setText(list.getJSONObject(4).getJSONObject("main").getString("temp"));

            TextView et11 = (TextView) findViewById(R.id.temp_15);
            et11.setText(list.getJSONObject(5).getJSONObject("main").getString("temp"));

            TextView et12 = (TextView) findViewById(R.id.temp_18);
            et12.setText(list.getJSONObject(6).getJSONObject("main").getString("temp"));

            TextView et13 = (TextView) findViewById(R.id.temp_21);
            et13.setText(list.getJSONObject(7).getJSONObject("main").getString("temp"));

        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
        }


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
