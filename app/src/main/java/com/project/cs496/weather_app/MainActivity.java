package com.project.cs496.weather_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity{
    private String fb_id;
    private String fb_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF666));
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
            Toast.makeText(this, "wow3", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.map_button) {
            Intent gotomap = new Intent(this, GPSandGoogleMap.class);
            startActivityForResult(gotomap, 2);
            return true;
        }

        if(id == android.R.id.home) {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
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
