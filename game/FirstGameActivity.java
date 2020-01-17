
package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FirstGameActivity extends AppCompatActivity {

    FirstGameView firstview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        //Decide which mode to start the game
        Bundle h = getIntent().getExtras();
        int choose = h.getInt("knight");

        firstview = new FirstGameView(this, choose);
        setContentView(firstview);
    }

    public void gameOver() {
        Intent intent = new Intent(this, FirstGameOver.class);
        Bundle b = new Bundle();

        int score = firstview.getManager().getScore();
        long duration = firstview.getManager().getDuration();
        int distance = firstview.getManager().getDistance();

        b.putInt("score", score);
        b.putLong("time", duration);
        b.putInt("distance", distance);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}