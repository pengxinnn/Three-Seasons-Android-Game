package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignIn:
                Intent SignInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(SignInIntent);
                break;
            case R.id.SignUp:
                Intent SignUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(SignUpIntent);
                break;
            case R.id.quickstart:
                Intent QuickStartIntent = new Intent(getApplicationContext(), StartGameActivity.class);
                startActivity(QuickStartIntent);
                break;
        }
    }
}

