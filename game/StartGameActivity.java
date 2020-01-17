package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);

    }

    @Override
    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGame:
                Intent NewGameIntent = new Intent(getApplicationContext(), Customization.class);
                startActivity(NewGameIntent);
                break;
            case R.id.viewRecords:
                //TODO
                break;
        }
    }

}
