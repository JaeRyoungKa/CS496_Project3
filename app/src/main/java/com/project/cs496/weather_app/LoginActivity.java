package com.project.cs496.weather_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by q on 2016-07-11.
 */
public class LoginActivity extends AppCompatActivity{
    private TextView status_text;
    private LoginButton fb_login_button;
    private CallbackManager callbackManager;
    private AccessTokenTracker fbTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        status_text = (TextView)findViewById(R.id.status_text);
        fb_login_button = (LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();


        manage_login();

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                status_text.setText("로그인 성공!" + "\n" + "User_ID: " + loginResult.getAccessToken().getUserId());

                manage_login();
            }

            @Override
            public void onCancel() {
                status_text.setText("로그인이 취소되었습니다.");

                manage_login();

            }

            @Override
            public void onError(FacebookException error) {
                status_text.setText("로그인이 실패하였습니다.");

                manage_login();
         }
        });

        fbTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                   status_text.setText("로그아웃완료!");
                }
            }
        };
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }

    private void manage_login() {
        try {
            Intent returnIntent = getIntent();
            if(AccessToken.getCurrentAccessToken().getUserId() != null && AccessToken.getCurrentAccessToken().getToken()!=null){
                returnIntent.putExtra("user_id",AccessToken.getCurrentAccessToken().getUserId());
                returnIntent.putExtra("user_token",AccessToken.getCurrentAccessToken().getToken());
                setResult(Activity.RESULT_OK,returnIntent);
            }else {
                setResult(Activity.RESULT_CANCELED);
            }
        } catch(Exception e) {
            setResult(Activity.RESULT_CANCELED);
        }
    }



}
