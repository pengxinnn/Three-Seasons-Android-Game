package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mainGameOver extends AppCompatActivity {
    TextView mainSummary;
    TextView totalScore;
    TextView averageScore;
    TextView totalTime;
    TextView score;
    TextView tTime;
    TextView aTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_over);

        mainSummary = findViewById(R.id.mainsummary);
        totalScore = findViewById(R.id.totalscore);
        averageScore = findViewById(R.id.averagetime);
        totalTime = findViewById(R.id.totaltime);
        score = findViewById(R.id.score);
        tTime = findViewById(R.id.ttime);
        aTime = findViewById(R.id.atime);

        int total_score = Constant.first_score + Constant.second_score + Constant.third_score;
        int total_time = (int) (Constant.first_duration + Constant.second_time + Constant.third_time);

        score.setText(String.valueOf(total_score));
        tTime.setText(String.valueOf(total_time) + "''");
        aTime.setText(String.valueOf(total_time / 3) + "''");

        Button button1 = (Button)findViewById(R.id.Try_Again);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mainGameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
