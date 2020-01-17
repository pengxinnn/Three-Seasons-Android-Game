package com.example.game;

import android.widget.ImageButton;
import android.content.Context;
import android.widget.TextView;

public class ThirdGameManager {

    /*  ------------------------------- Instance Variables ---------------------------------  */

    private ThirdGameActivity thirdGameActivity;

    private Context context;

    // Current step of the game
    private int step = 1;

    // The score achieved by the player in level 3 so far.
    private int score;

    // The total number of moves the player has performed.
    private int numMoves = 0;

    // Text that displays time since game started
    private TextView time;

    // Time since game started
    private int finalTime = 0;

    // Timer that counts the time
    CountUpTimer countUpTimer;

    // Constants representing the four possible positions for pictures
    private int UP_LEFT = 1;
    private int UP_RIGHT = 2;
    private int DOWN_LEFT = 3;
    private int DOWN_RIGHT = 4;

    // ImageButtons to be used in this game
    ImageButton Button1_1;
    ImageButton Button1_2;
    ImageButton Button_b1;
    ImageButton Button_b2;
    ImageButton Button_b3;
    ImageButton Button2_1;
    ImageButton Button2_2;
    ImageButton Button3_1;
    ImageButton Button3_2;
    ImageButton Button4_1;
    ImageButton Button4_2;
    ImageButton Button5_1;


    // Picture in up-left position
    private Picture upLeftPicture;

    // Picture in up-right position
    private Picture upRightPicture;

    // Picture in down-left position
    private Picture downLeftPicture;

    // Picture in down-right position
    private Picture downRightPicture;

    /*  ------------------------------------- Methods -------------------------------------  */

    ThirdGameManager(Context context, ThirdGameActivity thirdGameActivity) {
        this.context = context;
        this.thirdGameActivity = thirdGameActivity;
        time = thirdGameActivity.findViewById(R.id.time);

        initPictures();

        countUpTimer = new CountUpTimer(300000000) {
            public void onTick(int second) {
                String text = "Time: " + second;
                time.setText(text);
                finalTime++;
            }
        };
        countUpTimer.start();
    }

    private void initPictures() {

        Button1_1 = thirdGameActivity.findViewById(R.id.step1_1);
        Button1_2 = thirdGameActivity.findViewById(R.id.step1_2);
        Button_b1 = thirdGameActivity.findViewById(R.id.blank1);
        Button_b2 = thirdGameActivity.findViewById(R.id.blank2);
        Button_b3 = thirdGameActivity.findViewById(R.id.blank3);
        Button2_1 = thirdGameActivity.findViewById(R.id.step2_1);
        Button2_2 = thirdGameActivity.findViewById(R.id.step2_2);
        Button3_1 = thirdGameActivity.findViewById(R.id.step3_1);
        Button3_2 = thirdGameActivity.findViewById(R.id.step3_2);
        Button4_1 = thirdGameActivity.findViewById(R.id.step4_1);
        Button4_2 = thirdGameActivity.findViewById(R.id.step4_2);
        Button5_1 = thirdGameActivity.findViewById(R.id.step5_1);

        Picture p1 = new Picture(context, Button1_1);
        Picture p2 = new Picture(context, Button_b1);
        Picture p3 = new Picture(context, Button_b2);
        Picture p4 = new Picture(context, Button1_2);

        setUpLeftPicture(p1);
        setUpRightPicture(p2);
        setDownLeftPicture(p3);
        setDownRightPicture(p4);

        p1.setActivePic(p4);
        p1.setNextButton(Button2_1);
        p4.setActivePic(p1);
        p4.setNextButton(Button2_1);
        p3.setNextButton(Button2_2);
        p2.setNextButton(Button3_2);

    }


    /* ----------- Getters and Setters for private attributes. ----------- */

    int getFinalTime() {
        return finalTime;
    }

    ImageButton getButton_b3() {
        return Button_b3;
    }

    ImageButton getButton4_1() {
        return Button4_1;
    }

    ImageButton getButton4_2() {
        return Button4_2;
    }

    ImageButton getButton5_1() {
        return Button5_1;
    }

    int getStep() {
        return step;
    }

    void addStep() {
        step += 1;
    }

    ImageButton getButton3_1() {
        return Button3_1;
    }

    int getNumMoves() {
        return numMoves;
    }

    void addNumMoves() {
        numMoves += 1;
    }

    int getScore() {
        return score;
    }

    void addScore(int add) {
        score += add;
    }

    Picture getUpLeftPicture() {
        return upLeftPicture;
    }

    void setUpLeftPicture(Picture upLeftPicture) {
        this.upLeftPicture = upLeftPicture;
        upLeftPicture.setPosition(UP_LEFT);
    }

    Picture getUpRightPicture() {
        return upRightPicture;
    }

    void setUpRightPicture(Picture upRightPicture) {
        this.upRightPicture = upRightPicture;
        upRightPicture.setPosition(UP_RIGHT);
    }

    Picture getDownLeftPicture() {
        return downLeftPicture;
    }

    void setDownLeftPicture(Picture downLeftPicture) {
        this.downLeftPicture = downLeftPicture;
        downLeftPicture.setPosition(DOWN_LEFT);
    }

    Picture getDownRightPicture() {
        return downRightPicture;
    }

    void setDownRightPicture(Picture downRightPicture) {
        this.downRightPicture = downRightPicture;
        downRightPicture.setPosition(DOWN_RIGHT);
    }
}
