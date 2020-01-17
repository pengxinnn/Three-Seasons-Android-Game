package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondGameOver extends AppCompatActivity implements View.OnClickListener {
    TextView score;
    TextView finaltime;
    TextView failure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_gameover);
        score = findViewById(R.id.finalscore);
        finaltime = findViewById(R.id.finaltime);
        failure = findViewById(R.id.numFailure);
        // Get the statistics passed from the SecondGameActivity.
        Bundle b = getIntent().getExtras();
        int y = b.getInt("score");
        int x = b.getInt("time");
        int z = b.getInt("failure");

        Constant.second_score = y;
        Constant.second_time = x;
        Constant.second_failure = z;

        score.setText("FINAL SCORE:" + y);
        finaltime.setText("Total time:" + x + "''");
        failure.setText("Failures:" + z);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextlevel3:
                Intent CreateNewIntent = new Intent(getApplicationContext(), ThirdGameActivity.class);
                startActivity(CreateNewIntent);
        }
    }
}
