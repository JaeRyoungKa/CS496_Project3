package com.project.cs496.weather_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity{
    private String fb_id;
    private String fb_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
