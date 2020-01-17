package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_signin);
    }

    @Override
    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                break;
            case R.id.createnew:
                Intent CreateNewIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(CreateNewIntent);
        }
    }

}
