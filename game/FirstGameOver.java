package com.example.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstGameOver extends AppCompatActivity {

    TextView score;
    TextView duration;
    TextView distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game_over);
        score = (TextView) findViewById(R.id.score);
        duration = (TextView) findViewById(R.id.duration);
        distance = (TextView) findViewById(R.id.distance);
        Bundle b = getIntent().getExtras();
        int y = b.getInt("score");
        long x = b.getLong("time");
        int z = b.getInt("distance");
        score.setText("FINAL SCORE:" + y);
        duration.setText("Total time:" + x + "''");
        distance.setText("Distance:" + z);


        Constant.first_score = y;
        Constant.first_duration = x;
        Constant.first_distance = z;

        Button button1 = (Button)findViewById(R.id.next_level);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FirstGameOver.this, SecondGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
