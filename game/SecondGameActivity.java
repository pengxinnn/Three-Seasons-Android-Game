package com.example.game;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Color;
import android.widget.TextView;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
public class SecondGameActivity extends AppCompatActivity {
    // The score achieved by the player in level 2 so far.
    int score = 0;
    // The number of failures the player had in level 2 so far.
    int failure = 0;
    int finaltime = 0;
    Command currentC;
    TextView command;
    TextView scoreLable;
    // TextView of the countdowntimer.
    TextView time;
    // TextView of the countuptimer.
    TextView time2;
    TextView failureLable;
    // The countdowntimer used in each round.
    CountDownTimer countDownTimer;
    // The countuptimer used to record the time the player used in this game.
    CountUpTimer countUpTimer;
    ImageButton button1, button2, button3;
    // Whether this game is finished.
    boolean finished = false;
    // The remaining time of the countdowntimer.
    int timeValue = 5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondgame);
        currentC = new Command();
        command = findViewById(R.id.command);
        time = findViewById(R.id.timer);
        time2 = findViewById(R.id.timer2);
        failureLable = findViewById(R.id.failures);
        scoreLable = findViewById(R.id.score);
        ImageButton button1 = findViewById(R.id.red);
        ImageButton button2 = findViewById(R.id.yellow);
        ImageButton button3 = findViewById(R.id.green);
        // Set a new command.
        setCommandView();
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAnswer(Color.RED);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAnswer(Color.YELLOW);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAnswer(Color.GREEN);
            }
        });
        // A countdowntimer counting from 5 to 1.
        countDownTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(timeValue) + "\"");
                //With each iteration decrement the time by 1 sec
                timeValue -= 1;
            }

            //Now user is out of time
            public void onFinish() {
                // If number of failures of this player is less than 5, give a new command and
                // restart the countdowntimer.
                failure++;
                failureLable.setText("Failure : " + failure);
                if (failure < 5) {
                    currentC = new Command();
                    setCommandView();
                    countDownTimer.cancel();
                    timeValue = 5;
                    countDownTimer.start();
                }
                // If number of failures of this player >= 5, finish the game and SecondGameOver.
                else if (failure >= 5 && !finished){
                    Intent intent = new Intent(SecondGameActivity.this, SecondGameOver.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score);
                    b.putInt("time", finaltime);
                    b.putInt("failure", failure);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                    finished = true;
                }
            }
        }.start();
        countUpTimer = new CountUpTimer(3000000) {
            public void onTick(int second) {
                time2.setText(String.valueOf(second));
                finaltime ++;
            }
        };
        countUpTimer.start();
    }
    // Check the answer and update score the number of failures.
    public void getAnswer(int color) {
        if (currentC.commandColor() == color) {
            score++;
            scoreLable.setText("Score : " + score);
        } else {
            failure++;
            failureLable.setText("Failure : " + failure);
        }
        // Give new command and restart the countdowntimer.
        if (failure < 5) {
            countDownTimer.cancel();
            timeValue = 5;
            countDownTimer.start();
            currentC = new Command();
            setCommandView();
        // If number of failures of this player >= 5, finish the game and SecondGameOver.
        } else if (failure >= 5 && !finished) {
            Intent intent = new Intent(SecondGameActivity.this, SecondGameOver.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            b.putInt("time", finaltime);
            b.putInt("failure", failure);
            intent.putExtras(b);
            startActivity(intent);
            finish();
            finished = true;
        }
    }
    // Set the new command with new color, shape and word.
    private void setCommandView() {
        command.setText(currentC.getWord());
        command.setTextColor(currentC.getColor());
        if (currentC.getShape() == 0)
            command.setBackgroundResource(R.drawable.round);
        else
            command.setBackgroundResource(R.drawable.rectangle);
    }
}
