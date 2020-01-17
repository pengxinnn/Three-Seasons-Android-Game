package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ThirdGameOverActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView score;
    private TextView time;
    private TextView moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.third_gameover);

        score = (TextView) findViewById(R.id.score);
        time = (TextView) findViewById(R.id.finaltime);
        moves = (TextView) findViewById(R.id.numMove);
        Bundle b = getIntent().getExtras();
        int s = b.getInt("Score");
        int t = b.getInt("Time");
        int m = b.getInt("Moves");

        Constant.third_score = s;
        Constant.third_time = t;
        Constant.third_moves = m;

        score.setText("FINAL SCORE: " + s);
        time.setText("TOTAL TIME: " + t + "''");
        moves.setText("MOVES: " + m);
    }

    @Override
    public void onClick(View v) {
        Intent GameOverIntent = new Intent(getApplicationContext(), mainGameOver.class);
        startActivity(GameOverIntent);
    }
}

