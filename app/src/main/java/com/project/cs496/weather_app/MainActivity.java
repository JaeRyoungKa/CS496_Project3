package com.project.cs496.weather_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity{
    private String fb_id;
    private String fb_token;
    String sfName = "myFile";
    private double latitude;
    private double longitude;
    private String place_name;

    //Context resolveContext;

    private TextView location_name;
    private TextView current_temper;
    private TextView days;
    private TextView date;
    private ImageView weather_icon;
    private LineChart chart;
    private Calendar cal;



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
       // resolveContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(00,191,255)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

       // getWindow().getDecorView().setBackgroundColor(Color.rgb(135,206,250));

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        String latitude = sf.getString("latitude", ""); // 키값으로 꺼냄
        String longitude = sf.getString("longitude", "");



        place_name = sf.getString("place_name","");

        days = (TextView) findViewById(R.id.days);

        date = (TextView) findViewById(R.id.date);

        location_name = (TextView) findViewById(R.id.location_name);

        current_temper = (TextView) findViewById(R.id.current_temper);

        description_box = (TextView) findViewById(R.id.detail);

        weather_icon = (ImageView) findViewById(R.id.weather_icon);

        chart = (LineChart) findViewById(R.id.chart);

        cal = Calendar.getInstance();

        /*et6 = (TextView) findViewById(R.id.temp_00);
        et7 = (TextView) findViewById(R.id.temp_03);
        et8 = (TextView) findViewById(R.id.temp_06);
        et9 = (TextView) findViewById(R.id.temp_09);
        et10 = (TextView) findViewById(R.id.temp_12);
        et11 = (TextView) findViewById(R.id.temp_15);
        et12 = (TextView) findViewById(R.id.temp_18);
        et13 = (TextView) findViewById(R.id.temp_21);
*/
        location_name.setText(place_name);

        date.setText(DateFormat.getDateInstance().format(new Date()));

        chart.setDrawBorders(false); // disable drawing the chart borders(lines surrounding the chart)
        chart.setKeepPositionOnRotation(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.setDescription("");
        chart.getAxisLeft().setEnabled(false);
        chart.setTouchEnabled(false);


        Ion.with(this)
                .load((new WeatherHttpClient()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            JsonObject main = result.getAsJsonObject("main");
                            Double current_temperature = Double.parseDouble(main.get("temp").toString());

                            current_temper.setText(String.valueOf(current_temperature.intValue()) + (char) 0x00B0);
                            // main --> object 중 temp 항목 빼오는 과정 그리고 setText함

                            JsonObject weather = result.getAsJsonArray("weather").get(0).getAsJsonObject();

                            String icon_id = weather.get("icon").getAsString();
                            Ion.with(weather_icon)
                                    .load((new WeatherHttpClient()).getIconUrl(icon_id));
                        }catch(Exception exception){

                        }

                    }
                });

        Ion.with(this)
                .load((new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try{
                            JsonArray list = result.getAsJsonArray("list");

                            ArrayList<String> labels = new ArrayList<String>();

                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 3) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 6) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 9) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 12) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 15) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 18) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 21) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 24) %24) + "시");


                            ArrayList<Entry> entries = new ArrayList<Entry>();

                            entries.add(new Entry(Float.valueOf(list.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 0));
                            entries.add(new Entry(Float.valueOf(list.get(1).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 1));
                            entries.add(new Entry(Float.valueOf(list.get(2).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 2));
                            entries.add(new Entry(Float.valueOf(list.get(3).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 3));
                            entries.add(new Entry(Float.valueOf(list.get(4).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 4));
                            entries.add(new Entry(Float.valueOf(list.get(5).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 5));
                            entries.add(new Entry(Float.valueOf(list.get(6).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 6));
                            entries.add(new Entry(Float.valueOf(list.get(7).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 7));

                            LineDataSet dataset = new LineDataSet(entries, "온도");

                            LineData data = new LineData(labels, dataset);
                            dataset.setDrawFilled(true);

                            dataset.setCircleColor(-65281);
                            dataset.setValueTextSize(12f);


                            chart.setData(data);

                            chart.invalidate();



                        } catch(Exception exception) {

                        }
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



        location_name.setText(place_name);

        Ion.with(this)
                .load((new WeatherHttpClient()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            JsonObject main = result.getAsJsonObject("main");
                            Double current_temperature = Double.parseDouble(main.get("temp").toString());

                            current_temper.setText(String.valueOf(current_temperature.intValue()) + (char) 0x00B0);
                            // main --> object 중 temp 항목 빼오는 과정 그리고 setText함

                            JsonObject weather = result.getAsJsonArray("weather").get(0).getAsJsonObject();

                            String icon_id = weather.get("icon").getAsString();
                            Ion.with(weather_icon)
                                    .load((new WeatherHttpClient()).getIconUrl(icon_id));
                        }catch(Exception exception){

                        }

                    }
                });

        Ion.with(this)
                .load((new WeatherHttpClient2()).getWeatherDataUrl(latitude, longitude))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try{
                            JsonArray list = result.getAsJsonArray("list");

                            ArrayList<String> labels = new ArrayList<String>();

                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 3) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 6) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 9) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 12) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 15) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 18) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 21) % 24) + "시");
                            labels.add(String.valueOf((cal.get(Calendar.HOUR) + 24) %24) + "시");


                            ArrayList<Entry> entries = new ArrayList<Entry>();

                            entries.add(new Entry(Float.valueOf(list.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 0));
                            entries.add(new Entry(Float.valueOf(list.get(1).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 1));
                            entries.add(new Entry(Float.valueOf(list.get(2).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 2));
                            entries.add(new Entry(Float.valueOf(list.get(3).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 3));
                            entries.add(new Entry(Float.valueOf(list.get(4).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 4));
                            entries.add(new Entry(Float.valueOf(list.get(5).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 5));
                            entries.add(new Entry(Float.valueOf(list.get(6).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 6));
                            entries.add(new Entry(Float.valueOf(list.get(7).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString()), 7));

                            LineDataSet dataset = new LineDataSet(entries, "온도");

                            LineData data = new LineData(labels, dataset);
                            dataset.setDrawFilled(true);

                            dataset.setCircleColor(-65281);
                            dataset.setValueTextSize(12f);


                            chart.setData(data);

                            chart.invalidate();



                        } catch(Exception exception) {

                        }
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

       /* if(id == R.id.map_button) {
            Intent gotomap = new Intent(this, GPSandGoogleMap.class);
            startActivityForResult(gotomap, 3);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                fb_id = data.getStringExtra("user_id");
                fb_token = data.getStringExtra("user_token");
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
