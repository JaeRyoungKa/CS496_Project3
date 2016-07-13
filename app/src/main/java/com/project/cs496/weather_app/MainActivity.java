package com.project.cs496.weather_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.vision.text.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF666));

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        String str = sf.getString("Lat", ""); // 키값으로 꺼냄
        String str2 = sf.getString("Long", "");

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String data = ((new WeatherHttpClient()).getWeatherData(str, str2));
        String data2 = ((new WeatherHttpClient2()).getWeatherData(str, str2));
        try {
            JSONObject reader = new JSONObject(data);
            JSONObject reader2 = new JSONObject(data2);
            JSONArray list = reader2.getJSONArray("list");
            JSONObject main = reader.getJSONObject("main");
            JSONArray weather = reader.getJSONArray("weather");
            JSONObject des = weather.getJSONObject(0);
            JSONObject th1 = list.getJSONObject(0);
            JSONObject thd1 = th1.getJSONObject("main");
            String temp = main.getString("temp");
            TextView et3 = (TextView) findViewById(R.id.temperature);
            et3.setText(temp);
            TextView et4 = (TextView) findViewById(R.id.location);
            String temp2 = reader.getString("name");
            et4.setText(temp2);
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
            Toast.makeText(this, "wow2", Toast.LENGTH_SHORT).show();

            return true;
        }

        if(id == R.id.setting_button) {
            Intent setting_intent = new Intent(this,SettingActivity.class);
            startActivityForResult(setting_intent,3);
            return true;
        }

        if(id == R.id.map_button) {
            Intent gotomap = new Intent(this, GPSandGoogleMap.class);
            startActivityForResult(gotomap, 2);
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
        }

    }
}
